# investment-tax-relief-submission-dynamic-stub

[![Apache-2.0 license](http://img.shields.io/badge/license-Apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Build Status](https://travis-ci.org/hmrc/investment-tax-relief-submission-dynamic-stub.svg?branch=master)](https://travis-ci.org/hmrc/investment-tax-relief-submission-dynamic-stub) [ ![Download](https://api.bintray.com/packages/hmrc/releases/investment-tax-relief-submission-dynamic-stub/images/download.svg) ](https://bintray.com/hmrc/releases/investment-tax-relief-submission-dynamic-stub/_latestVersion)

This is a stub for the Tax Relief Submission Service. The stub is a test double that supports the Tax Relief Submission Service REST API in development or test environments, this enables testing of clients of the service without requiring a full end-to-end test environment that has all the backend services and systems available.

The stub is a Play/Scala application backed by a Mongo database for the test data, which can be dynamically created (hence it is termed a dynamic stub, because it does not contain hardcoded, static test data). The test data can be set up either by making requests to the relevant apply or amend operations of the API, or directly loaded into the database using e.g. `mongoimport`. 

This stub also implements various test scenarios (success/failure http responses) based on the email address int he submission JSON.

The stub supports these Tax Relief Submission Service API operations:

API
----

| PATH | Supported Methods |
|------|-------------------|
|Simulate Submission of an advanced assurance application and various HTTP responses: |
|```/taxpayers/:tavcReferenceId/returns``` | POST |
|Get the company registration details for specified safeID:|
|```/details``` | GET |

   
Requirements
------------

This service is written in [Scala](http://www.scala-lang.org/) and [Play](http://playframework.com/), so needs at least a [JRE] to run.


## Run the application


To update from Nexus and start all services from the RELEASE version instead of snapshot

```
sm --start TAVC_ALL -f
```

 
##To run the application locally execute the following:


Kill the service ```sm --stop ITR_SUBM_DYNAMIC_STUB``` in service Manager and run:
```
sbt 'run 9639' 
```
*This service is part of the investment tax relief service and has dependent services.*
*For a full list of the dependent microservices that comprise this service please see the readme for our* [Submission Frontend Service](https://github.com/hmrc/investment-tax-relief-submission-frontend/)


### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")

Example advanced assurance JSON
````
{
	"acknowledgementReference": "ABCD1234564646",
	"submissionType": {
		"agentReferenceNumber": "AARN1234567",
		"correspondenceDetails": {
			"contactName": {
				"name1": "John",
				"name2": "Smith"
			},
			"contactDetails": {
				"phoneNumber": "000 000000",
				"mobileNumber": "00 0000000",
				"faxNumber": "00 00000000",
				"emailAddress": "john.smith@nowsuchpalcecounrtyorvenueXX.com"
			},
			"contactAddress": {
				"addressLine1": "Line 1",
				"addressLine2": "Line 2",
				"addressLine3": "line 3",
				"addressLine4": "Line 4",
				"postalCode": "AA1 1AA",
				"countryCode": "GB"
			}
		},
		"organisationType": "Limited",
		"submission": {
			"advancedAssurance": {
				"schemeTypes": {
					"eis": true,
					"seis": false,
					"sitr": false,
					"vct": true
				},
				"trade": {
					"businessActivity": "Preparing To Trade",
					"baDescription": "abcd description",
					"marketInfo": {
						"newGeographicMarket": false,
						"newProductMarket": false,
						"marketDescription": "market Description"
					},
					"dateTradeCommenced": "2012-12-21",
					"annualCosts": {
						"annualCost": [{
							"periodEnding": "2013",
							"operatingCost": {
								"amount": "1500",
								"currency": "GBP"
							},
							"researchAndDevelopmentCost": {
								"amount": "1300",
								"currency": "GBP"
							}
						}, {
							"periodEnding": "2014",
							"operatingCost": {
								"amount": "1600",
								"currency": "GBP"
							},
							"researchAndDevelopmentCost": {
								"amount": "1400",
								"currency": "GBP"
							}
						}]
					},
					"annualTurnover": {
						"annualTurnover": [{
							"periodEnding": "2013",
							"turnover": {
								"amount": "1500",
								"currency": "GBP"
							}
						}, {
							"periodEnding": "2014",
							"turnover": {
								"amount": "1600",
								"currency": "GBP"
							}
						}, {
							"periodEnding": "2015",
							"turnover": {
								"amount": "1300",
								"currency": "GBP"
							}
						}]
					},
					"previousOwnership": {
						"dateAcquired": "2012-03-31",
						"prevOwnerStartDate": "2012-03-31",
						"previousOwner": {
							"companyDetails": {
								"organisationName": "previousOwner organisationName",
								"ctUtr": "1234567890",
								"crn": "crn value",
								"companyAddress": {
									"addressLine1": "Line 1 Co",
									"addressLine2": "Line 2 Co",
									"addressLine3": "Line 3 Co",
									"addressLine4": "line 4 Co",
									"postalCode": "AA1 1AA",
									"countryCode": "AD"
								}
							}
						}
					}
				},
				"proposedInvestment": {
					"growthJustification": "abcd growthJustification",
					"unitType": "Debentures",
					"investmentAmount": {
						"amount": "2500",
						"currency": "GBP"
					}
				},
				"subsidiaryPerformingTrade": {
					"ninetyPercentOwned": false,
					"companyDetails": {
						"organisationName": "subsidiary Performing Trade company",
						"ctUtr": "1234567890",
						"crn": "crn value",
						"companyAddress": {
							"addressLine1": "Line 1",
							"addressLine2": "Line 2",
							"addressLine3": "Line 3",
							"addressLine4": "Line 4",
							"postalCode": "AA1 1LA",
							"countryCode": "AD"
						}
					}
				},
				"knowledgeIntensive": {
					"skilledEmployeesConditionMet": true,
					"innovationConditionMet": true,
					"kiConditionMet": true
				},
				"organisation": {
					"utr": "1234567890",
					"chrn": "0987654321",
					"startDate": "2012-03-31",
					"firstDateOfCommercialSale": "2013-03-31",
					"orgDetails": {
						"organisationName": "previousOwner organisationName",
						"ctUtr": "1234567890",
						"crn": "crn value",
						"companyAddress": {
							"addressLine1": "Line 1",
							"addressLine2": "Line 2",
							"addressLine3": "Line 3",
							"addressLine4": "Line 4",
							"postalCode": "AA1 1AA",
							"countryCode": "AD"
						}
					},
					"subsidiaries": {
						"subsidiary": [{
							"companyRegNumber": "1234567890",
							"companyDetails": {
								"organisationName": "subsidiary 1 company",
								"companyAddress": {
									"addressLine1": "Line 1",
									"addressLine2": "Line 2",
									"addressLine3": "Line 3",
									"addressLine4": "Line 4",
									"postalCode": "AA1 1AA",
									"countryCode": "GB"
								}
							}
						}, {
							"companyRegNumber": "0987654321",
							"companyDetails": {
								"organisationName": "subsidiary 2 company",
								"companyAddress": {
									"addressLine1": "Line 1",
									"addressLine2": "Line 2",
									"addressLine3": "Line 3",
									"addressLine4": "Line 4",
									"postalCode": "AA1 1AA",
									"countryCode": "GB"
								}
							}
						}]
					},
					"previousRFIs": {
						"previousRFI": [{
							"schemeType": "EIS",
							"name": "RFI 1 name",
							"issueDate": "2012-11-21",
							"amount": {
								"amount": "1321",
								"currency": "GBP"
							},
							"amountSpent": {
								"amount": "1021",
								"currency": "GBP"
							}
						}, {
							"schemeType": "SEIS",
							"name": "RFI 2 name",
							"issueDate": "2013-11-21",
							"amount": {
								"amount": "1221",
								"currency": "GBP"
							},
							"amountSpent": {
								"amount": "1121",
								"currency": "GBP"
							}
						}]
					}
				}
			}
		}
	}
}
```
