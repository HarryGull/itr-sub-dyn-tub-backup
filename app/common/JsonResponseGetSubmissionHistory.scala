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

package common

import play.api.libs.json.Json

object JsonResponseGetSubmissionHistory {

  // XITAVC000904056
  val submissionEisVcTResponses = Json.parse(
    """
    |{
    |	"processingDate": "2017-09-22T10:30:06Z",
    |	"countReturned": "6",
    |	"countTotal": "6",
    |	"submissions": [{
    |		"formBundleNumber": "000000123451",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2015-09-22",
    |		"schemeType": [{
    |			"scheme": "EIS"
    |		}, {
    |			"scheme": "VCT"
    |		}],
    |		"status": "Received",
    |		"contactNoteReference": "003333333333"
    |	}, {
    |		"formBundleNumber": "000000126631",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2015-09-20",
    |		"schemeType": [{
    |			"scheme": "EIS"
    |		}, {
    |			"scheme": "VCT"
    |		}],
    |		"status": "Received",
    |		"contactNoteReference": "003334443334"
    |	}, {
    |		"formBundleNumber": "000000193451",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2015-09-19",
    |		"schemeType": [{
    |			"scheme": "EIS"
    |		}, {
    |			"scheme": "VCT"
    |		}],
    |		"status": "Accepted",
    |		"contactNoteReference": "003355533334"
    |	}, {
    |		"formBundleNumber": "000000155451",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2015-08-23",
    |		"schemeType": [{
    |			"scheme": "EIS"
    |		}, {
    |			"scheme": "VCT"
    |		}],
    |		"status": "Rejected",
    |		"contactNoteReference": "003666333334"
    |	}, {
    |		"formBundleNumber": "000000193477",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2014-09-24",
    |		"schemeType": [{
    |			"scheme": "EIS"
    |		}, {
    |			"scheme": "VCT"
    |		}],
    |		"status": "Rejected",
    |		"contactNoteReference": "003336663334"
    |	}, {
    |		"formBundleNumber": "000000856488",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2014-09-25",
    |		"schemeType": [{
    |			"scheme": "EIS"
    |		}, {
    |			"scheme": "VCT"
    |		}],
    |		"status": "Accepted",
    |		"contactNoteReference": "003333777334"
    |	}]
    |}""".stripMargin
  )

  // XYTAVC000796389
  val
  submissionsFullCombinedResponsesNoSitr = Json.parse(
    """
  |{
  |	"processingDate": "2017-09-22T10:30:06Z",
  |	"countReturned": "6",
  |	"countTotal": "6",
  |	"submissions": [{
  |		"formBundleNumber": "000000123451",
  |		"submissionType": "Advance Assurance",
  |		"submissionDate": "2015-09-22",
  |		"schemeType": [{
  |			"scheme": "EIS"
  |		}, {
  |			"scheme": "VCT"
  |		}, {
  |			"scheme": "SEIS"
  |		}],
  |		"status": "Received",
  |		"contactNoteReference": "003333333333"
  |	}, {
  |		"formBundleNumber": "000000126631",
  |		"submissionType": "Advance Assurance",
  |		"submissionDate": "2015-09-20",
  |		"schemeType": [{
  |			"scheme": "EIS"
  |		}, {
  |			"scheme": "VCT"
  |		}, {
  |			"scheme": "SEIS"
  |		}],
  |		"status": "Received",
  |		"contactNoteReference": "003444333334"
  |	}, {
  |		"formBundleNumber": "000000193451",
  |		"submissionType": "Advance Assurance",
  |		"submissionDate": "2015-09-19",
  |		"schemeType": [{
  |			"scheme": "EIS"
  |		}, {
  |			"scheme": "VCT"
  |		}],
  |		"status": "Accepted",
  |		"contactNoteReference": "003335553334"
  |	}, {
  |		"formBundleNumber": "000000155451",
  |		"submissionType": "Advance Assurance",
  |		"submissionDate": "2015-08-23",
  |		"schemeType": [{
  |			"scheme": "EIS"
  |		}, {
  |			"scheme": "VCT"
  |		}, {
  |			"scheme": "SEIS"
  |		}],
  |		"status": "Rejected",
  |		"contactNoteReference": "003333666334"
  |	}, {
  |		"formBundleNumber": "000000193477",
  |		"submissionType": "Advance Assurance",
  |		"submissionDate": "2014-09-24",
  |		"schemeType": [{
  |			"scheme": "SEIS"
  |		}, {
  |			"scheme": "VCT"
  |		}],
  |		"status": "Rejected",
  |		"contactNoteReference": "003337773334"
  |	}, {
  |		"formBundleNumber": "000000856488",
  |		"submissionType": "Advance Assurance",
  |		"submissionDate": "2014-09-25",
  |		"schemeType": [{
  |			"scheme": "SEIS"
  |		}, {
  |			"scheme": "VCT"
  |		}],
  |		"status": "Accepted",
  |		"contactNoteReference": "003338883334"
  |	}]
  |}""".stripMargin
  )

  //XVTAVC000945313
  val submissionsSingleSchemesResponsesNoSitr = Json.parse(
    """
    |{
    |	"processingDate": "2017-09-22T10:30:06Z",
    |	"countReturned": "6",
    |	"countTotal": "6",
    |	"submissions": [{
    |		"formBundleNumber": "000000123451",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2015-09-22",
    |		"schemeType": [{
    |			"scheme": "EIS"
    |		}],
    |		"status": "Received",
    |		"contactNoteReference": "003333333333"
    |	}, {
    |		"formBundleNumber": "000000126631",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2015-09-20",
    |		"schemeType": [{
    |			"scheme": "SEIS"
    |		}],
    |		"status": "Received",
    |		"contactNoteReference": "003333333334"
    |	}, {
    |		"formBundleNumber": "000000193451",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2015-09-19",
    |		"schemeType": [{
    |			"scheme": "VCT"
    |		}],
    |		"status": "Accepted",
    |		"contactNoteReference": "003333333334"
    |	}, {
    |		"formBundleNumber": "000000155451",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2015-08-23",
    |		"schemeType": [{
    |			"scheme": "SEIS"
    |		}],
    |		"status": "Rejected",
    |		"contactNoteReference": "003333333334"
    |	}, {
    |		"formBundleNumber": "000000193477",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2014-09-24",
    |		"schemeType": [{
    |			"scheme": "EIS"
    |		}],
    |		"status": "Rejected",
    |		"contactNoteReference": "003333333334"
    |	}, {
    |		"formBundleNumber": "000000856488",
    |		"submissionType": "Advance Assurance",
    |		"submissionDate": "2014-09-25",
    |		"schemeType": [{
    |			"scheme": "VCT"
    |		}],
    |		"status": "Rejected",
    |		"contactNoteReference": "003333333334"
    |	}]
    |}
  """.stripMargin
  )

}
