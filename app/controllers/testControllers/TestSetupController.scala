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

package controllers.testControllers

import models.Error
import models.submission.SubmissionResponse
import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.play.microservice.controller.BaseController
import play.api.mvc.{Action, BodyParsers, Results}
import mongo.InvestmentTaxReliefSubmissionRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TestSetupController extends TestSetupController {
  val investmentTaxReliefSubmissionRepository = InvestmentTaxReliefSubmissionRepository()
}

trait TestSetupController extends BaseController {

  val investmentTaxReliefSubmissionRepository : InvestmentTaxReliefSubmissionRepository

  // TODO: model would be not be SubmissionResponse as below. Will change to a realistic model later
  def insertSubmissionRecord() : Action[JsValue] = Action.async(BodyParsers.parse.json) { implicit request =>
    val submissionRecordJs = request.body.validate[SubmissionResponse]
    submissionRecordJs.fold(
      errors => Future.successful(BadRequest(Json.toJson(Error(reason = "" + errors)))),
      submissionRequest =>
        investmentTaxReliefSubmissionRepository.insert(submissionRequest).map {
          _ => Ok
        }
          .recover {
            case exception => Results.InternalServerError(exception.toString)
          }
    )
  }

  val wipeDataBase = Action.async(parse.anyContent) { implicit request =>
    investmentTaxReliefSubmissionRepository.wipeTestData().map {
      _ => Ok("Cleansed")
    }
  }
}
