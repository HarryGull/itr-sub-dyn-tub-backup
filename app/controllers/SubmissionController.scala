/*
 * Copyright 2017 HM Revenue & Customs
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

import common.{Constants, JsonResponseGetSubmissionHistory, TavcReferenceConstants}
import common.Validation._
import uk.gov.hmrc.play.microservice.controller.BaseController
import models._
import models.submission.SubmissionResponse
import play.api.libs.json.{JsError, JsResult, JsValue, Json}
import play.api.mvc.{Action, _}

import scala.concurrent.Future
import mongo.InvestmentTaxReliefSubmissionRepository
import play.api.{Logger, http}
import utils.SchemaHelper

import scala.concurrent.ExecutionContext.Implicits.global

object SubmissionStubController extends SubmissionStubController{
  val investmentTaxReliefSubmissionRepository = InvestmentTaxReliefSubmissionRepository()
}

trait SubmissionStubController extends BaseController {

  val response = (message: String) => s"""{"reason" : "$message"}"""

  val investmentTaxReliefSubmissionRepository : InvestmentTaxReliefSubmissionRepository

  //noinspection ScalaStyle
  def submitAdvancedAssuranceApplication(tavcReferenceId: String) = Action.async (BodyParsers.parse.json) { implicit request =>

    val jsonBody = request.body.toString()

    val validationReport = SchemaHelper.getJsonValidationReport(jsonBody)

    if (validationReport.fold(true)(_.isSuccess == false)) {
      Future.successful(BadRequest(Json.toJson(Error(
        reason = "Request to submit application failed with validation errors:" + validationReport.toString))))
    }
    else {
      val emailFromJson = Json.parse(jsonBody).as[EmailModel]
      val emailLower = emailFromJson.emailAddress.getOrElse("").toLowerCase()

      Logger.info(s"JsonSubmitted for submitAdvancedAssuranceApplication is: $jsonBody")

      // return faked expected responses for testing based on the email value passed.
      emailLower match {
        case email if email.contains("badrequest") => {
          Future.successful(BadRequest(Json.toJson(Error(
            reason = "Request to submit application failed with validation errors"))))
        }
        case email if email.contains("forbiddenrequest") => {
          Future.successful(Forbidden(Json.toJson(Error(reason = "Forbidden"))))
        }
        case email if email.contains("notfound") => {
          Future.successful(NotFound(Json.toJson(Error(reason = "Not Found"))))
        }
        case email if email.contains("internalservererrorrequest") => {
          Future.successful(InternalServerError(Json.toJson(Error(reason = "Internal Server Error"))))
        }
        case email if email.contains("serviceunavailablerequest") => {
          Future.successful(ServiceUnavailable(Json.toJson(Error(reason = "Service Unavailable"))))
        }
        case email if email.contains("getsubmittedjson") => {
          Logger.info(s"[SubscriptionStubController][getsubmittedJson] is: $jsonBody")
          Future.successful(BadRequest(Json.toJson(Error(reason = jsonBody))))
        }
        case _ => {
          Future.successful(Ok(Json.toJson(SubmissionResponse("2014-12-17T09:30:47Z", generateFormBundleId()))))
        }
      }
    }
  }

  def getReturnsSummary(tavcReferenceId: String, arn: Option[String]): Action[AnyContent] = Action.async { implicit request =>
    if (tavcIdValidationCheck(tavcReferenceId))
      getSubmissionHistorySummaryResponse(tavcReferenceId)
    else Future.successful(BadRequest(response(Constants.oneOrMoreErrors)))
  }

  //noinspection ScalaStyle
  private def getSubmissionHistorySummaryResponse(tavcReferenceId: String): Future[Result] = {
    tavcReferenceId match {
      case TavcReferenceConstants.notFoundRef => {
        Future.successful(NotFound)
      }
      case TavcReferenceConstants.badRequestRefOneOrMoreErrors => {
        Future.successful(BadRequest(response(Constants.oneOrMoreErrors)))
      }
      case TavcReferenceConstants.badRequestRefInvalidJsonMessage => {
        Future.successful(BadRequest(response(Constants.invalidMessageReceived)))
      }
      case TavcReferenceConstants.badRequesDuplicateSubmissionRef => {
        Future.successful(BadRequest(response(Constants.err004)))
      }
      case TavcReferenceConstants.resourceNotFoundRef => {
        Future.successful(NotFound(response(Constants.resourceNotFound)))
      }
      case TavcReferenceConstants.serverErrorRef => {
        Future.successful(InternalServerError(response(Constants.serverError)))
      }
      case TavcReferenceConstants.serverErrorRegimeRef => {
        Future.successful(InternalServerError(response(Constants.err001)))
      }
      case TavcReferenceConstants.serverErrorSAPmissingRef => {
        Future.successful(InternalServerError(response(Constants.err002)))
      }
      case TavcReferenceConstants.serviceUnavailableNotRespondingRef => {
        Future.successful(ServiceUnavailable(response(Constants.serviceUnavailable)))
      }
      case TavcReferenceConstants.serviceUnavailable003Ref => {
        Future.successful(ServiceUnavailable(response(Constants.err003)))
      }
      case TavcReferenceConstants.serviceUnavailable999Ref => {
        Future.successful(ServiceUnavailable(response(Constants.err999)))
      }
      case _ => getMatchingJsonSubmissions(tavcReferenceId)
    }
  }

  //noinspection ScalaStyle
  private def getMatchingJsonSubmissions(tavcReferenceId: String): Future[Result] = {

    val json = tavcReferenceId match {
      case TavcReferenceConstants.historyAAEisVctAllRef =>  JsonResponseGetSubmissionHistory.submissionEisVcTResponses
      case TavcReferenceConstants.historyAAWithCombinedNoSitr =>  JsonResponseGetSubmissionHistory.submissionsFullCombinedResponsesNoSitr
      case TavcReferenceConstants.historyAASingleSchemesNoSitr =>  JsonResponseGetSubmissionHistory.submissionsSingleSchemesResponsesNoSitr
      // default The JSON to return in all cases unless a specific TAVCRef is passed to match
      case _ => JsonResponseGetSubmissionHistory.submissionsFullCombinedResponsesNoSitr
    }

    Future(Status(http.Status.OK)(json))
  }

  def generateFormBundleId(): String = {
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
    play.Logger.info("Request headers: environment =" + environment.getOrElse(notSet) + ", authorisation=" +
      token.getOrElse(notSet))

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
