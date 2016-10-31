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
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}

class RegistrationControllerSpec extends UnitSpec with WithFakeApplication {

  val otherSafeID = "XA0000000000000"

  object TestController extends RegistrationController

  "getRegistrationDetails" when {

    "The minimum safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.minimumRegSafeID).apply(FakeRequest())

      "return OK" in {
        status(result) shouldBe OK
      }

      "return minimum registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.minimumRegResponse
      }

    }

    "The max address safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.maxAddressRegSafeID).apply(FakeRequest())

      "return OK" in {
        status(result) shouldBe OK
      }

      "return max address registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.maxAddressRegResponse
      }

    }

    "The max contact details safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.maxContactDetailsRegSafeID).apply(FakeRequest())

      "return OK" in {
        status(result) shouldBe OK
      }

      "return max contact details registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.maxContactDetailsRegResponse
      }

    }

    "The maximum details safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.maximumRegSafeID).apply(FakeRequest())

      "return OK" in {
        status(result) shouldBe OK
      }

      "return maximum registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.maximumRegResponse
      }

    }

    "The failure safe ID is passed" should {

      lazy val result = TestController.getRegistrationDetails(Constants.failedRegSafeID).apply(FakeRequest())

      "return OK" in {
        status(result) shouldBe BAD_REQUEST
      }

      "return maximum registration details JSON" in {
        Json.parse(contentAsString(result)) shouldBe JsonResponses.failedRegResponse
      }

    }
  }

  "Any other safe ID is passed" should {

    lazy val result = TestController.getRegistrationDetails(otherSafeID).apply(FakeRequest())

    "return OK" in {
      status(result) shouldBe OK
    }

    "return maximum registration details JSON" in {
      Json.parse(contentAsString(result)) shouldBe JsonResponses.maximumRegResponse
    }

  }
}
