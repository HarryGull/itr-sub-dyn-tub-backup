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

import auth.{Authorisation, Authorised, NotAuthorised}
import common.{Constants, JsonResponses}
import play.Logger
import play.api.mvc.{Action, AnyContent}
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

object RegistrationController extends RegistrationController

trait RegistrationController extends BaseController with Authorisation {

  //noinspection ScalaStyle
  def getRegistrationDetails(safeid: String): Action[AnyContent] = Action.async { implicit request =>
    authorised {
      case Authorised => {
        Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "Authorised")
        safeid match {
          case Constants.minimumRegSafeID => {
            Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "MinReg")
            Future.successful(Ok(JsonResponses.minimumRegResponse))
          }
          case Constants.maxAddressRegSafeID => {
            Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "MaxAddress")
            Future.successful(Ok(JsonResponses.maxAddressRegResponse))
          }
          case Constants.maxContactDetailsRegSafeID => {
            Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "MaxContact")
            Future.successful(Ok(JsonResponses.maxContactDetailsRegResponse))
          }
          case Constants.maximumRegSafeID => {
            Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "MaxReg")
            Future.successful(Ok(JsonResponses.maximumRegResponse))
          }
          case Constants.submissionErrorSafeID => {
            Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "Subm error")
            Future.successful(BadRequest(JsonResponses.submissionErrorResponse))
          }
          case Constants.resourceNotFoundSafeID => {
            Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "Not Found")
            Future.successful(NotFound(JsonResponses.resourceNotFoundResponse))
          }
          case Constants.serverErrorSafeID => {
            Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "ServerError")
            Future.successful(InternalServerError(JsonResponses.serverErrorResponse))
          }
          case Constants.serviceUnavailableSafeID => {
            Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "ServiceUnavalable")
            Future.successful(ServiceUnavailable(JsonResponses.serviceUnavailableResponse))
          }
          case _ => {
            Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "Other")
            Future.successful(Ok(JsonResponses.maximumRegResponse))
          }
        }
      }
      case NotAuthorised(error) => {
        Logger.info(s"[RegistrationController][getRegistrationDetails] - " +  "UnAuthorised")
        Future.successful(Unauthorized(error))
      }
    }
  }

}
