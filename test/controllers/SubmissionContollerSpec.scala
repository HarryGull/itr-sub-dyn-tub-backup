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

import fixtures.SubmissionFixture
import models.submission.SubmissionResponse
import mongo.InvestmentTaxReliefSubmissionRepository
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.test.UnitSpec
import org.scalatest.mock.MockitoSugar
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatestplus.play.OneAppPerSuite


class SubmissionControllerSpec extends UnitSpec with OneAppPerSuite with MockitoSugar with SubmissionFixture {

  val mockRepository: InvestmentTaxReliefSubmissionRepository = mock[InvestmentTaxReliefSubmissionRepository]

  val tavcReferenceId:String = "AA1234567890000"

  implicit lazy val actorSystem = ActorSystem()
  implicit lazy val mat = ActorMaterializer()

  private class Setup {

    object TestController extends SubmissionStubController {
      val investmentTaxReliefSubmissionRepository: InvestmentTaxReliefSubmissionRepository = mockRepository
    }

  }

  "The stub should return a  " should {
    "return a valid json response with a bundle id and expected message detailing the status" in new Setup {
      val result = TestController.submitAdvancedAssuranceApplication(tavcReferenceId).apply(FakeRequest().withBody(validJs))
      val submissionResponse: SubmissionResponse = jsonBodyOf(result).as[SubmissionResponse]
      submissionResponse.formBundleNumber.startsWith("FBUND") shouldEqual true
      submissionResponse.processingDate shouldEqual "2014-12-17T09:30:47Z"
      status(result) shouldBe OK

    }
  }

  "The stub should return an internal server error if the email contains the text internalservererror " should {
    "return a json package detailing the status" in new Setup {
      val result = TestController.submitAdvancedAssuranceApplication(tavcReferenceId).apply(FakeRequest().withBody(InternalServerErrJs))
      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
  }

  "The stub should return an bad request server error if the schema validation of the JSON fails " should {
    "return a json package detailing the status" in new Setup {
      val result = TestController.submitAdvancedAssuranceApplication(tavcReferenceId).apply(FakeRequest().withBody(invalidSchemaJs))
      status(result) shouldBe BAD_REQUEST
    }
  }

  "The stub should return a service unavailable error if the email contains the text serviceunavailable " should {
    "return a json package detailing the status" in new Setup {
      val result = TestController.submitAdvancedAssuranceApplication(tavcReferenceId).apply(FakeRequest().withBody(unavailableJs))
      status(result) shouldBe SERVICE_UNAVAILABLE
    }
  }

  "The stub should return a service unavailable error if the email contains the text forbidden " should {
    "return a json package detailing the status" in new Setup {
      val result = TestController.submitAdvancedAssuranceApplication(tavcReferenceId).apply(FakeRequest().withBody(forbiddenJS))
      status(result) shouldBe FORBIDDEN
    }
  }

  "The stub should return a bad request if the email contains the text badrequest " should {
    "return a json package detailing the status" in new Setup {
      val resultd = TestController.submitAdvancedAssuranceApplication(tavcReferenceId).apply(FakeRequest().withBody(badRequestJs))
      status(resultd) shouldBe BAD_REQUEST
    }
  }
}
