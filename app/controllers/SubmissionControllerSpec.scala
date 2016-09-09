/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import uk.gov.hmrc.play.microservice.controller.BaseController
import model._
import play.api.libs.json.{JsError, JsResult, JsValue, Json}
import play.api.mvc._

import scala.concurrent.Future
import SubmissionResponse.formats
import SubmissionRequest.format
import mongo.InvestmentTaxReliefSubmissionRepository

//THE BELOW ARE REQUIRED
import ContactDetailsModel.format
import YourCompanyNeedModel.format


/**
  * The controller for the Investment Tax Relief Submission service REST API dynamic stub
  *
  **/

object SubmissionStubController extends SubmissionStubController{
  val investmentTaxReliefSubmissionRepository = InvestmentTaxReliefSubmissionRepository()
}

trait SubmissionStubController extends BaseController {

  val investmentTaxReliefSubmissionRepository : InvestmentTaxReliefSubmissionRepository


  def testResponse(nino: String): Action[AnyContent] = Action.async{ implicit request =>
    val result = SubmissionResponseTest(true, nino, "Service Response Success")
    Future.successful(Ok(Json.toJson(result)))
  }

  //noinspection ScalaStyle
  def submitAdvancedAssuranceApplication = Action.async (BodyParsers.parse.json) { implicit request =>

    val submissionApplicationBodyJs = request.body.validate[SubmissionRequest]
    //val headers = request.headers.toSimpleMap
    //val submissionApplicationBodyJs = ControllerHelper.addExtraRequestHeaderChecks(headers, submissionApplicationBodyJs)

    submissionApplicationBodyJs.fold(
      errors => Future.successful(BadRequest(Json.toJson(Error(message="Request to submit application failed with validation errors: " + errors)))),
      submitRequest => {

        val emailLower = submitRequest.contactDetails.email.toLowerCase()
        // Some simple faked responses based on the email input parameter
        // This is a just a simple temporary approach until we have the proper submission/JSON and APIs
        emailLower match {
          case email if email.contains("badrequest") => {
            Future.successful(BadRequest(Json.toJson(Error(message="Request to submit application failed with validation errors" ))))
          }
          case email if email.contains("forbiddenrequest") => {
            Future.successful(Forbidden(Json.toJson(Error(message="Forbidden" ))))
          }
          case email if email.contains("internalservererrorrequest") => {
            Future.successful(InternalServerError(Json.toJson(Error(message="Internal Server Error" ))))
          }
          case email if email.contains("serviceunavailablerequest") => {
            Future.successful(ServiceUnavailable(Json.toJson(Error(message="Service Unavailable" ))))
          }
          case _ => {
            Future.successful(Created(Json.toJson(SubmissionResponse(true, generateFormbundleId(),
              "Submission Request Successful"))))
          }
        }
      }
    )


  }

  def generateFormbundleId(): String = {
    val start = 10000000
    val end =   99999999
    // prepend a string value
    s"FBUND${start + scala.util.Random.nextInt( (end - start) + 1 )}"
  }

}

object ControllerHelper {
  /*
 * Checks that the standard extra headers required for NPS requests are present in a request
 * @param headers a simple map of all request headers
 * @param the result of validating the request body
 * @rreturn the overall validation result, of non-success then will include both body and  header validation errors
 */
  def addExtraRequestHeaderChecks[T](headers: Map[String, String], bodyValidationResultJs: JsResult[T]): JsResult[T] = {
    val environment = headers.get("Environment")
    val token = headers.get("Authorization")
    val notSet = "<NOT SET>"
    play.Logger.info("Request headers: environment =" + environment.getOrElse(notSet) + ", authorisation=" + token.getOrElse(notSet))

    //  Ensure any header validation errors are accumulated with any body validation errors into a single JsError
    //  (the below code is not so nice, could be a good use case for scalaz validation)
    val noAuthHeaderErr = JsError("required header 'Authorisation' not set in NPS request")
    val noEnvHeaderErr = JsError("required header 'Environment' not set in NPS request")
    // 1. accumlate any header errors
    def headerNotPresentErrors: Option[JsError] = (environment, token) match {
      case (Some(_), Some(_)) => None
      case (Some(_), None) => Some(noAuthHeaderErr)
      case (None, Some(_)) => Some(noEnvHeaderErr)
      case (None, None) => Some(noAuthHeaderErr ++ noEnvHeaderErr)
    }
    // 2. accumulate any header + any body errors
    (bodyValidationResultJs, headerNotPresentErrors) match {
      case (e1: JsError, e2: Some[JsError]) => e1 ++ e2.get
      case (e1: JsError, _) => e1
      case (_, e2: Some[JsError]) => e2.get
      case _ => bodyValidationResultJs // success case
    }
  }
}
