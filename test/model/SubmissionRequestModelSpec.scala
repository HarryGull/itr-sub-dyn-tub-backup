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

package model

import model.{ContactDetailsModel, SubmissionRequest, YourCompanyNeedModel}
import play.api.libs.json.Json
import uk.gov.hmrc.play.test.UnitSpec

class SubmissionRequestModelSpec extends UnitSpec {

  val testJson = """{"contactDetails":{"forename":"gary","surname":"hull","telephoneNumber":"01952 256555","email":"fred@fred.com"},"yourCompanyNeedModel":{"needAAorCS":"AA"}}"""

  // form json to model - unapply
  "call unapply successfully to create ss Json" in {
    implicit val formats = Json.format[SubmissionRequest]
    val cd = ContactDetailsModel("gary", "hull", "01952 256555", "fred@fred.com")
    val yd = YourCompanyNeedModel("AA")
    val sub = new SubmissionRequest(cd, yd)


    val json = Json.toJson(sub)
    println(json)
    println(testJson)
    json.toString() shouldBe testJson

  }
}

