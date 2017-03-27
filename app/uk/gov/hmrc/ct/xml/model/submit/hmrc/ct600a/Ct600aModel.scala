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

package uk.gov.hmrc.ct.xml.model.submit.hmrc.ct600a

import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever

case class Ct600aModel(companyInformation: CompanyInformation,
                       section1: Section1,
                       section2: Section2,
                       section3: Section3,
                       section4: Section4,
                       section5: Section5)

object Ct600aModel {
  def apply(boxValueRetriever: CT600ABoxRetriever): Ct600aModel = ???
}

//object Ct600aModel extends BoxRetrieverFactory with RetrieverTypeConverter {
//
//  def apply(boxValueRetriever: CT600ABoxRetriever with FilingAwareBoxRetriever): Ct600aModel = {
//    new Ct600aModel(CompanyInformation(boxValueRetriever.filing), Section1(boxValueRetriever), Section2(boxValueRetriever), Section3(boxValueRetriever), Section4(boxValueRetriever), Section5(boxValueRetriever))
//  }
//}
