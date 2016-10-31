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

import common.{Constants, JsonResponses}
import play.api.mvc.{Action, AnyContent}
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

object RegistrationController extends RegistrationController

trait RegistrationController extends BaseController {

  def getRegistrationDetails(safeid: String): Action[AnyContent] = Action.async {
    implicit request =>
      safeid match {
        case Constants.minimumRegSafeID => Future.successful(Ok(JsonResponses.minimumRegResponse))
        case Constants.maxAddressRegSafeID => Future.successful(Ok(JsonResponses.maxAddressRegResponse))
        case Constants.maxContactDetailsRegSafeID => Future.successful(Ok(JsonResponses.maxContactDetailsRegResponse))
        case Constants.maximumRegSafeID => Future.successful(Ok(JsonResponses.maximumRegResponse))
        case Constants.maximumRegSafeID => Future.successful(Ok(JsonResponses.maximumRegResponse))
        case Constants.failedRegSafeID => Future.successful(BadRequest(JsonResponses.failedRegResponse))
        case _ => Future.successful(Ok(JsonResponses.maximumRegResponse))
      }
  }

}
