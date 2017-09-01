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
import models.submission.ErrorResponse
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.test.UnitSpec
import org.scalatest.mock.MockitoSugar
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import common.{Constants, JsonResponseGetSubmissionHistory, TavcReferenceConstants}
import org.scalatestplus.play.OneAppPerSuite
import play.api.libs.json.Json


class SubmissionControllerGetHistorySpec extends UnitSpec with OneAppPerSuite with MockitoSugar with SubmissionFixture {

  //val mockRepository: InvestmentTaxReliefSubmissionRepository = mock[InvestmentTaxReliefSubmissionRepository]

  val tavcReferenceId:String = "AA1234567890000"

  implicit lazy val actorSystem = ActorSystem()
  implicit lazy val mat = ActorMaterializer()

  private class Setup {

    object TestController extends SubmissionStubController {
      //val investmentTaxReliefSubmissionRepository: InvestmentTaxReliefSubmissionRepository = mockRepository
    }

  }

  "When the XVTAVC000280665 reference is passed the stub" should {
    "return the expected not found and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.notFoundRef, None).apply(FakeRequest())
      status(result) shouldBe NOT_FOUND
    }
  }

  "When the XVTAVC000043633 reference is passed the stub" should {
    "return the expected bad request and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.badRequestRefOneOrMoreErrors, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.oneOrMoreErrors
      status(result) shouldBe BAD_REQUEST
    }
  }

  "When the XLTAVC000453774 reference is passed the stub" should {
    "return the expected bad request and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.badRequestRefInvalidJsonMessage, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.invalidJsonMessageReceived
      status(result) shouldBe BAD_REQUEST
    }
  }

  "When the XZTAVC000655021 reference is passed the stub" should {
    "return the expected bad request and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.badRequesDuplicateSubmissionRef, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.err004
      status(result) shouldBe BAD_REQUEST
    }
  }

  "When the XZTAVC000885031 reference is passed the stub" should {
    "return the expected not found and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.resourceNotFoundRef, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.resourceNotFound
      status(result) shouldBe NOT_FOUND
    }
  }

  "When the XUTAVC000548324 reference is passed the stub" should {
    "return the expected internal server error and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.serverErrorRef, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.serverError
      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
  }

  "When the XLTAVC000657291 reference is passed the stub" should {
    "return the expected internal server error and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.serverErrorRegimeRef, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.err001
      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
  }

  "When the XPTAVC000808267 reference is passed the stub" should {
    "return the expected internal server error and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.serverErrorSAPmissingRef, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.err002
      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
  }

  "When the XXTAVC000829816 reference is passed the stub" should {
    "return the expected service unavailable and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.serviceUnavailableNotRespondingRef, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.serviceUnavailable
      status(result) shouldBe SERVICE_UNAVAILABLE
    }
  }

  "When the XWTAVC000321083 reference is passed the stub" should {
    "return the expected service unavailable and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.serviceUnavailable003Ref, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.err003
      status(result) shouldBe SERVICE_UNAVAILABLE
    }
  }

  "When the XYTAVC000960695 reference is passed the stub" should {
    "return the expected service unavailable and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.serviceUnavailable999Ref, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.err999
      status(result) shouldBe SERVICE_UNAVAILABLE
    }
  }

  "When the invalid tavc reference XYTA000960695 is passed the stub" should {
    "return the expected bad request and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.invalidRef1, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.oneOrMoreErrors
      status(result) shouldBe BAD_REQUEST
    }
  }

  "When the invalid tavc reference XYTAVD000960695 is passed the stub" should {
    "return the expected bad request and error message response" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.invalidRef2, None).apply(FakeRequest())
      val errorResponse: ErrorResponse = jsonBodyOf(result).as[ErrorResponse]
      errorResponse.reason shouldBe Constants.oneOrMoreErrors
      status(result) shouldBe BAD_REQUEST
    }
  }

  "When the valid tavc reference XYTAVC000796389 is passed the stub" should {
    "return the expected OK request with a JSON Result" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.historyAAWithCombinedNoSitr, None).apply(FakeRequest())
      val json = Json.parse(contentAsString(result))
      json.toString() shouldBe JsonResponseGetSubmissionHistory.submissionsFullCombinedResponsesNoSitr.toString()

      status(result) shouldBe OK
    }
  }

  "When the valid tavc reference XITAVC000904056 is passed the stub" should {
    "return the expected OK request with a JSON Result" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.historyAAEisVctAllRef, None).apply(FakeRequest())
      val json = Json.parse(contentAsString(result))
      json.toString() shouldBe JsonResponseGetSubmissionHistory.submissionEisVcTResponses.toString()

      status(result) shouldBe OK
    }
  }

  "When the valid tavc reference XVTAVC000945313 is passed the stub" should {
    "return the expected OK request with a JSON Result" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.historyAASingleSchemesNoSitr, None).apply(FakeRequest())
      val json = Json.parse(contentAsString(result))
      json.toString() shouldBe JsonResponseGetSubmissionHistory.submissionsSingleSchemesResponsesNoSitr.toString()

      status(result) shouldBe OK
    }
  }
  "When the valid tavc reference XVTAVC000349574 is passed the stub" should {
    "return the expected OK request with no previous submission history records JSON Result" in new Setup {
      val result = TestController.getReturnsSummary(TavcReferenceConstants.noPreviousSubmissionsRef, None).apply(FakeRequest())
      val json = Json.parse(contentAsString(result))
      json.toString() shouldBe JsonResponseGetSubmissionHistory.noPreviousSubmissionsResponse.toString()

      status(result) shouldBe OK
    }
  }
}
