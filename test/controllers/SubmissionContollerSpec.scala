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

import fixtures.SubmissionFixture
import mongo.InvestmentTaxReliefSubmissionRepository
import org.scalatest.mock.MockitoSugar
import org.mockito.Matchers
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}


class SubmissionControllerSpec extends UnitSpec with WithFakeApplication with MockitoSugar with SubmissionFixture {

  val mockRepository = mock[InvestmentTaxReliefSubmissionRepository]

  class Setup {
    object TestController extends SubmissionStubController {
      val investmentTaxReliefSubmissionRepository = mockRepository
    }
  }

//TODO: The below tests need to be fixed. They are about there but something  not quite right..

//  "The stub should return a  " should {
//    "return a valid json response with a bundle id and expected message detailing the status" in new Setup {
//      //when(mockRepository.getSubmission(Matchers.eq("123456789"))).thenReturn(Future.successful(matchedSubmissionRequest))
//      val result = TestController.submitAdvancedAssuranceApplication().apply(FakeRequest().withJsonBody(validJs))
//
//      val submissionReponse: SubmissionResponse = jsonBodyOf(result).as[SubmissionResponse]
//      status(result) shouldBe Future.successful(OK)
//      submissionReponse.formBundleId.startsWith("FBUND") shouldEqual true
//      submissionReponse.message shouldBe "Submission Request Successful"
//      submissionReponse.status shouldEqual true
//
//    }
//  }
//
//  "The stub should return a bad request if the email contains the text badrequest " should {
//    "return a json package detailing the status" in new Setup {
//      //when(mockRepository.getSubmission(Matchers.eq("123456789"))).thenReturn(Future.successful(matchedSubmissionRequest))
//      val result = TestController.submitAdvancedAssuranceApplication().apply(FakeRequest().withJsonBody(badRequestJs))
//      status(result) shouldBe Future.successful(BAD_REQUEST)
//    }
//  }
//
//  "The stub should return an internal server error if the email contains the text internalservererror " should {
//    "return a json package detailing the status" in new Setup {
//      //when(mockRepository.getSubmission(Matchers.eq("123456789"))).thenReturn(Future.successful(matchedSubmissionRequest))
//      val result = TestController.submitAdvancedAssuranceApplication().apply(FakeRequest().withJsonBody(internalServerErrorJs))
//      status(result) shouldBe Future.successful(INTERNAL_SERVER_ERROR)
//    }
//  }
//
//  "The stub should return a service unavailable error if the email contains the text serviceunavailable " should {
//    "return a json package detailing the status" in new Setup {
//      //when(mockRepository.getSubmission(Matchers.eq("123456789"))).thenReturn(Future.successful(matchedSubmissionRequest))
//      val result = TestController.submitAdvancedAssuranceApplication().apply(FakeRequest().withJsonBody(serviceUnavilableJs))
//      status(result) shouldBe Future.successful(SERVICE_UNAVAILABLE)
//    }
//  }
//
//  "The stub should return a service unavailable error if the email contains the text forbidden " should {
//    "return a json package detailing the status" in new Setup {
//      //when(mockRepository.getSubmission(Matchers.eq("123456789"))).thenReturn(Future.successful(matchedSubmissionRequest))
//      val result = TestController.submitAdvancedAssuranceApplication().apply(FakeRequest().withJsonBody(forbiddenJs))
//      status(result) shouldBe Future.successful(FORBIDDEN)
//    }
//  }

}
