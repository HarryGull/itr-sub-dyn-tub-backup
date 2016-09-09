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

package fixtures

import model.{ContactDetailsModel, SubmissionRequest, YourCompanyNeedModel}
import play.api.libs.json.{JsValue, Json}

trait SubmissionFixture {

  implicit val formats = Json.format[SubmissionRequest]
  val companyDetails = ContactDetailsModel("gary", "hull", "01952 256555", "fred@fred.com")
  val yourdetails = YourCompanyNeedModel("AA")
  lazy val validSubmissionData = new SubmissionRequest(companyDetails, yourdetails)

  lazy val emailBadRequestJson = """{"contactDetails":{"forename":"gary","surname":"hull","telephoneNumber":"01952 256555","email":"badrequest@fred.com"},"yourCompanyNeedModel":{"needAAorCS":"AA"}}"""
  lazy val emailInternalServererrorJson = """{"contactDetails":{"forename":"gary","surname":"hull","telephoneNumber":"01952 256555","email":"eminternalservererrorrequest@fred.com"},"yourCompanyNeedModel":{"needAAorCS":"AA"}}"""
  lazy val emailserviceunavailablerRequestJson = """{"contactDetails":{"forename":"gary","surname":"hull","telephoneNumber":"01952 256555","email":"embserviceunavailablerequest@fred.com"},"yourCompanyNeedModel":{"needAAorCS":"AA"}}"""
  lazy val emailforbiddenRequestJson = """{"contactDetails":{"forename":"gary","surname":"hull","telephoneNumber":"01952 256555","email":"emforbiddenrequest@fred.com"},"yourCompanyNeedModel":{"needAAorCS":"AA"}}"""



  lazy val badRequestSubmission = validSubmissionData.copy(this.companyDetails.copy(email ="embadrequat@fred.com"))
  lazy val internalServerErrorRequestSubmission = validSubmissionData.copy(this.companyDetails.copy(email ="eminternalservererrorrequest@fred.com"))
  lazy val serviceUnavilableErrorRequestSubmission = validSubmissionData.copy(this.companyDetails.copy(email ="embserviceunavailablerequest@fred.com"))
  lazy val forbiddenErrorRequestSubmission = validSubmissionData.copy(this.companyDetails.copy(email ="emforbiddenrequest@fred.com"))


  lazy val validJs: JsValue = Json.toJson(validSubmissionData)
  lazy val badRequestJs = Json.toJson(badRequestSubmission)
  lazy val internalServerErrorJs = Json.toJson(internalServerErrorRequestSubmission)
  lazy val forbiddenJs: JsValue = Json.toJson(forbiddenErrorRequestSubmission)
  lazy val serviceUnavilableJs: JsValue = Json.toJson(serviceUnavilableErrorRequestSubmission)
}