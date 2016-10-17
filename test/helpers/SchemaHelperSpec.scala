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

package utils

import com.github.fge.jsonschema.core.report.ProcessingReport
import fixtures.SubmissionFixture
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}
import play.api.libs.json.{JsSuccess, _}
import models.submission.SubmissionResponse

object SchemaHelperSpec {

  val authHeader = "Authorization" -> "Bearer: abcdef12345678901234567890"
  val envHeader = "Environment" -> "IST0"

  val validHeadersExample = Map(authHeader,envHeader)
  val noAuthHeadersExample = Map(envHeader)
  val noEnvHeadersExample = Map(authHeader)
  val emptyHeadersExample = Map[String,String]()
  val invalidRequestJsError = JsError("invalid request body")

  implicit val formats = Json.format[SubmissionResponse]
  val validSubmissionResponse = SubmissionResponse("2014-12-17T09:30:47Z", "FBUND98763284")
}
class SchemaHelperSpec extends UnitSpec with WithFakeApplication with SubmissionFixture{

  "The schema getJsonValidationReport " should {
    "return None for the report if the json is empty" in {
            SchemaHelper.getJsonValidationReport("") shouldBe None
    }

    "return None for the report if the json is not parseable" in {
      SchemaHelper.getJsonValidationReport("{.kkk{}") shouldBe None
    }

    "return a valid report if the json is valid to the schema" in {
      val report = SchemaHelper.getJsonValidationReport(validSubmitJson.toString)
      report.fold(false)(_.isSuccess) shouldBe true
    }

    "return a valid report if the json has a valid structure but does not match schems definition" in {
      val report = SchemaHelper.getJsonValidationReport(invalidSchemaJson.toString)
      report.fold(true)(_.isSuccess) shouldBe false
    }
  }

  "The schema validateJson " should {
    "return None for the report if the json is empty" in {
      SchemaHelper.validateJson("") shouldBe false
    }

    "return None for the report if the json is not parseable" in {
      SchemaHelper.validateJson("{.kkk{}") shouldBe false
    }

    "return a valid report if the json is valid to the schema" in {
     SchemaHelper.validateJson(validSubmitJson.toString) shouldBe true
    }

    "return a valid report if the json has a valid structure but does not match schems definition" in {
      SchemaHelper.validateJson(invalidSchemaJson.toString) shouldBe false
    }
  }


//
//  "The validation results for an invalid testReponse and valid headers" should {
//    "return a failure with one error" in {
//      val requestJs = ControllerHelper.addExtraRequestHeaderChecks(TestData.validHeadersExample, TestData.invalidRequestJsError)
//      requestJs.isSuccess shouldBe false
//      requestJs.asInstanceOf[JsError].errors.seq.size shouldBe 1
//      requestJs.asInstanceOf[JsError].errors.seq.head._2.size shouldBe 1
//    }
//  }
//
//  "The validation results for a valid CreateProtectionRequest and missing Authorization and Environment headers" should {
//    "return a failure with two validation errosr" in {
//      val requestJs = ControllerHelper.addExtraRequestHeaderChecks(TestData.emptyHeadersExample, JsSuccess(TestData.validSubmissionResponse))
//      requestJs.isSuccess shouldBe false
//      println("RESULT ==> " + requestJs)
//      requestJs.asInstanceOf[JsError].errors.seq.size shouldBe 1
//      requestJs.asInstanceOf[JsError].errors.seq.head._2.size shouldBe 2
//    }
//  }
//
//  "The validation results for an invalid CreateProtectionRequest and missing Authorization and Environment headers" should {
//    "return a failure with three validation errosr" in {
//      val requestJs = ControllerHelper.addExtraRequestHeaderChecks(TestData.emptyHeadersExample,  TestData.invalidRequestJsError)
//      requestJs.isSuccess shouldBe false
//      println("RESULT ==> " + requestJs)
//      requestJs.asInstanceOf[JsError].errors.seq.size shouldBe 1
//      requestJs.asInstanceOf[JsError].errors.seq.head._2.size shouldBe 3
//    }
//  }
//
//  "The validation results for a valid CreateProtectionRequest and missing Environment header" should {
//    "return a failure with one error" in {
//      val requestJs = ControllerHelper.addExtraRequestHeaderChecks(TestData.noEnvHeadersExample, JsSuccess(TestData.validSubmissionResponse))
//      requestJs.isSuccess shouldBe false
//      requestJs.asInstanceOf[JsError].errors.seq.size shouldBe 1
//      requestJs.asInstanceOf[JsError].errors.seq.head._2.size shouldBe 1
//    }
//  }
//
//  "The validation results for a valid CreateProtectionRequest and missing Authorization header" should {
//    "return a failure with one error" in {
//      val requestJs = ControllerHelper.addExtraRequestHeaderChecks(TestData.noAuthHeadersExample, JsSuccess(TestData.validSubmissionResponse))
//      requestJs.isSuccess shouldBe false
//      requestJs.asInstanceOf[JsError].errors.seq.size shouldBe 1
//      requestJs.asInstanceOf[JsError].errors.seq.head._2.size shouldBe 1
//    }
  //}
}
