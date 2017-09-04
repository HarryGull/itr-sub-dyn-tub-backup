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

package helpers

import fixtures.SubmissionFixture
import play.api.libs.json.{JsError, Json}
import uk.gov.hmrc.play.test.UnitSpec
import models.submission.SubmissionResponse
import org.scalatestplus.play.OneAppPerSuite
import utils.SchemaHelper

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
class SchemaHelperSpec extends UnitSpec with OneAppPerSuite with SubmissionFixture{

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

}
