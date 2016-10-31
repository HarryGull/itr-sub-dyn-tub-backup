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

package common

import play.api.libs.json.Json

object JsonResponses {

  // SAFE ID: XA0001234567890
  val minimumRegResponse = Json.parse(
    """
      |{
      |   "sapNumber": "0123456789",
      |   "safeId": "XA0001234567890",
      |   "isEditable": true,
      |   "isAnAgent": false,
      |   "isAnIndividual": false,
      |   "organisation": {
      |    "organisationName": "Minimum Registration LTD"
      |   },
      |   "addressDetails": {
      |       "addressLine1": "line1",
      |       "addressLine2": "line2",
      |       "countryCode": "NZ"
      |   },
      |   "contactDetails": {
      |   }
      |}""".stripMargin
  )

  // SAFE ID: XA0002345678901
  val maxAddressRegResponse = Json.parse(
    """
      |{
      |  "sapNumber":"1234567890",
      |  "safeId":"XA0002345678901",
      |  "isEditable":true,
      |  "isAnAgent":false,
      |  "isAnIndividual":false,
      |  "organisation":{
      |    "organisationName":"Max Address Registration LTD"
      |  },
      |  "addressDetails":{
      |    "addressLine1":"line1",
      |    "addressLine2":"line2",
      |    "addressLine3":"line3",
      |    "addressLine4":"line4",
      |    "postalCode":"AB11AB",
      |    "countryCode":"GB"
      |  },
      |  "contactDetails":{
      |  }
      |}""".stripMargin
  )

  // SAFE ID: XA0003456789012
  val maxContactDetailsRegResponse = Json.parse(
    """
      |{
      |  "sapNumber":"2345678901",
      |  "safeId":"XA0003456789012",
      |  "isEditable":true,
      |  "isAnAgent":false,
      |  "isAnIndividual":false,
      |  "organisation":{
      |    "organisationName":"Max Contact Details Registration LTD"
      |  },
      |  "addressDetails":{
      |    "addressLine1":"line1",
      |    "addressLine2":"line2",
      |    "countryCode":"JP"
      |  },
      |  "contactDetails":{
      |    "phoneNumber":"01234567890",
      |    "mobileNumber":"01234567890",
      |    "faxNumber":"01234567890",
      |    "emailAddress":"test@test.com"
      |  }
      |}""".stripMargin
  )

  // SAFE ID: XA0004567890123
  val maximumRegResponse = Json.parse(
    """
      |{
      |  "sapNumber":"3456789012",
      |  "safeId":"XA0004567890123",
      |  "agentReferenceNumber":"AARN1234567",
      |  "nonUKIdentification":{
      |    "idNumber":"1",
      |    "issuingInstitution":"Test Institution",
      |    "issuingCountryCode":"NZ"
      |  },
      |  "isEditable":true,
      |  "isAnAgent":false,
      |  "isAnIndividual":false,
      |  "organisation":{
      |    "organisationName":"Max Contact Details Registration LTD",
      |    "isAGroup":false,
      |    "organisationType":"LLP"
      |  },
      |  "addressDetails":{
      |    "addressLine1":"line1",
      |    "addressLine2":"line2",
      |    "addressLine3":"line3",
      |    "addressLine4":"line4",
      |    "postalCode":"AB11AB",
      |    "countryCode":"GB"
      |  },
      |  "contactDetails":{
      |    "phoneNumber":"01234567890",
      |    "mobileNumber":"01234567890",
      |    "faxNumber":"01234567890",
      |    "emailAddress":"test@test.com"
      |  }
      |}""".stripMargin
  )

}
