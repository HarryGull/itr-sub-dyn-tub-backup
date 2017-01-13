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

import helpers.AuthHelpers._
import common.{Constants, JsonResponses}
import play.api.libs.json.Json
import play.api.test.Helpers._
import uk.gov.hmrc.play.test.UnitSpec
import org.scalatestplus.play.OneAppPerSuite

class RegistrationControllerSpec extends UnitSpec with OneAppPerSuite {

  val otherSafeID = "XA0000000000000"
  val noEnvResponse = "required header 'Environment' not set in ETMP request"

  object TestController extends RegistrationController

  "getRegistrationDetails with valid headers" when {

    "The minimum safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.minimumRegSafeID).apply(validRequest)

      "return OK" in {
        status(result) shouldBe OK
      }

      "return minimum registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.minimumRegResponse
      }

    }

    "The max address safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.maxAddressRegSafeID).apply(validRequest)

      "return OK" in {
        status(result) shouldBe OK
      }

      "return max address registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.maxAddressRegResponse
      }

    }

    "The max contact details safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.maxContactDetailsRegSafeID).apply(validRequest)

      "return OK" in {
        status(result) shouldBe OK
      }

      "return max contact details registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.maxContactDetailsRegResponse
      }

    }

    "The maximum details safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.maximumRegSafeID).apply(validRequest)

      "return OK" in {
        status(result) shouldBe OK
      }

      "return maximum registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.maximumRegResponse
      }

    }

    "The submission error safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.submissionErrorSafeID).apply(validRequest)

      "return OK" in {
        status(result) shouldBe BAD_REQUEST
      }

      "return submission error JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.submissionErrorResponse
      }

    }

    "The resource not found safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.resourceNotFoundSafeID).apply(validRequest)

      "return OK" in {
        status(result) shouldBe NOT_FOUND
      }

      "return submission error JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.resourceNotFoundResponse
      }

    }

    "The server error safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.serverErrorSafeID).apply(validRequest)

      "return OK" in {
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }

      "return submission error JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.serverErrorResponse
      }

    }

    "The service unavailable safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.serviceUnavailableSafeID).apply(validRequest)

      "return OK" in {
        status(result) shouldBe SERVICE_UNAVAILABLE
      }

      "return submission error JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.serviceUnavailableResponse
      }

    }

    "Any other safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(otherSafeID).apply(validRequest)

      "return OK" in {
        status(result) shouldBe OK
      }

      "return maximum registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.maximumRegResponse
      }

    }
  }

  "getRegistrationDetails with invalid headers" when {

    "Any safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(otherSafeID).apply(noEnvRequest)

      "return an error response" in {
        contentAsString(result) shouldBe noEnvResponse
      }

      "return UNAUTHORIZED" in {
        status(result) shouldBe UNAUTHORIZED
      }

    }
  }
}
