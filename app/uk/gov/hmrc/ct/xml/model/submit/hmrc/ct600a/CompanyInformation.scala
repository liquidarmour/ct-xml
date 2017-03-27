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

case class CompanyInformation(companyName: String, taxReference: String, fromDate: String, toDate: String)

//object CompanyInformation  extends BoxRetrieverFactory with RetrieverTypeConverter {
//  def apply(filing: Filing): CompanyInformation = {
//    val boxValueRetriever = retriever[V2TaxReturnBoxRetriever](createBoxRetriever(filing))
//    CompanyInformation(
//      companyName = boxValueRetriever.b155.value,
//      taxReference = filing.utr.value,
//      fromDate = boxValueRetriever.cp1().value.toString("dd/MM/yyyy"),
//      toDate = boxValueRetriever.cp2().value.toString("dd/MM/yyyy")
//    )
//  }
//}
