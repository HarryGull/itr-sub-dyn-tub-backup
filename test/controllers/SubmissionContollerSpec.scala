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
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.test.UnitSpec
import org.scalatest.mock.MockitoSugar
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import common.{Constants, SubmissionEmailConstants}
import org.scalatestplus.play.OneAppPerSuite


class SubmissionControllerSpec extends UnitSpec with OneAppPerSuite with MockitoSugar with SubmissionFixture {

  //val mockRepository: InvestmentTaxReliefSubmissionRepository = mock[InvestmentTaxReliefSubmissionRepository]

  val tavcReferenceId:String = "AA1234567890000"

  implicit lazy val actorSystem = ActorSystem()
  implicit lazy val mat = ActorMaterializer()

  private class Setup {

    object TestController extends SubmissionStubController {
      //val investmentTaxReliefSubmissionRepository: InvestmentTaxReliefSubmissionRepository = mockRepository
    }

  }

  "The stub should return a  " should {
    "return a valid json response with a bundle id and expected message detailing the status" in new Setup {
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(validJs))
      val submissionResponse: SubmissionResponse = jsonBodyOf(result).as[SubmissionResponse]
      submissionResponse.formBundleNumber.length shouldEqual 12
      submissionResponse.processingDate shouldEqual "2014-12-17T09:30:47Z"
      status(result) shouldBe OK

    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.notFoundEmail} The stub " should {
    "return a not found error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.notFoundEmail)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe NOT_FOUND
    }
  }


  s"If the email contains the text ${SubmissionEmailConstants.badRequestEmailOneOrMoreErrors} The stub " should {
    "return an bad request one or more errors error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.badRequestEmailOneOrMoreErrors)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe BAD_REQUEST
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.badRequestEmailInvalidJsonMessage} The stub " should {
    "return an bad request invalid json error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.badRequestEmailInvalidJsonMessage)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe BAD_REQUEST
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.badRequesDuplicateSubmissionEmail} The stub " should {
    "return an bad request invalid json error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.badRequesDuplicateSubmissionEmail)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe BAD_REQUEST
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.resourceNotFoundEmail} The stub " should {
    "return a resource not found error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.resourceNotFoundEmail)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe NOT_FOUND
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.serverErrorEmail} The stub " should {
    "return an internal server error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.serverErrorEmail)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.serverErrorRegimeEmail} The stub " should {
    "return an internal server error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.serverErrorRegimeEmail)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.serverErrorSAPmissingEmail} The stub " should {
    "return an internal server error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.serverErrorSAPmissingEmail)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.serviceUnavailable003Email} The stub " should {
    "return servive unavailable error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.serviceUnavailable003Email)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe SERVICE_UNAVAILABLE
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.serviceUnavailable999Email} The stub " should {
    "return servive unavailable error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.serviceUnavailable999Email)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe SERVICE_UNAVAILABLE
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.serviceUnavailableNotRespondingEmail} The stub " should {
    "return servive unavailable error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.serviceUnavailableNotRespondingEmail)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe SERVICE_UNAVAILABLE
    }
  }

  s"If the email contains the text ${SubmissionEmailConstants.forbidden} The stub " should {
    "return an forbidden error" in new Setup {
      val json = getErrorJson(SubmissionEmailConstants.forbidden)
      val result = TestController.submitApplication(tavcReferenceId).apply(FakeRequest().withBody(json))
      status(result) shouldBe FORBIDDEN
    }
  }
}
