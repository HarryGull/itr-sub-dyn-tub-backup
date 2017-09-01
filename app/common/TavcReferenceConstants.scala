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

object TavcReferenceConstants {

  //errors
  val notFoundRef = "XVTAVC000280665"
  val badRequestRef = "XOTAVC000266211"
  val badRequestRefOneOrMoreErrors  = "XVTAVC000043633"
  val badRequestRefInvalidJsonMessage = "XLTAVC000453774"
  val badRequesDuplicateSubmissionRef = "XZTAVC000655021"
  val resourceNotFoundRef = "XZTAVC000885031"
  val serverErrorRef = "XUTAVC000548324"
  val serverErrorRegimeRef = "XLTAVC000657291"
  val serverErrorSAPmissingRef = "XPTAVC000808267"
  val serviceUnavailable003Ref = "XWTAVC000321083"
  val serviceUnavailableNotRespondingRef = "XXTAVC000829816"
  val serviceUnavailable999Ref = "XYTAVC000960695"
  val invalidRef1 = "XYTA000960695"
  val invalidRef2 = "XYTAVD000960695"
  val noPreviousSubmissionsRef = "XVTAVC000349574"
  
  // submission history mappings
  val historyAAEisVctAllRef = "XITAVC000904056"
  val historyAASingleSchemesNoSitr = "XVTAVC000945313"
  val historyAAWithCombinedNoSitr = "XYTAVC000796389"

}

object SubmissionEmailConstants {

  //errors
  val notFoundEmail = "notfoundgeneral"
  val badRequestEmailOneOrMoreErrors  = "badrequestoneormore"
  val badRequestEmailInvalidJsonMessage = "badrequestinvalidjson"
  val badRequesDuplicateSubmissionEmail = "badrequestduplicate"
  val resourceNotFoundEmail = "resourcenotfound"
  val serverErrorEmail = "internalservererrorrequest"
  val serverErrorRegimeEmail = "internalservererror001"
  val serverErrorSAPmissingEmail = "internalservererror002"
  val serviceUnavailable003Email = "serviceunavailable003"
  val serviceUnavailable999Email = "serviceunavailabl999"
  val serviceUnavailableNotRespondingEmail = "serviceunavailablenotresponding"
  val forbidden = "forbiddenrequest"

}