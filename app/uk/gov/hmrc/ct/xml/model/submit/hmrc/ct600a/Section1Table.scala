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

case class Section1Table(rows: Seq[Section1Row])

//object Section1Table {
//
//  def apply(boxValueRetriever: CT600ABoxRetriever): Section1Table = {
//    Section1Table(getLoans(boxValueRetriever).map{Section1Row(_)})
//  }
//
//  private def getLoans(boxValueRetriever: CT600ABoxRetriever): Seq[Loan] = {
//    boxValueRetriever.lp02().loans.getOrElse(Nil)
//  }
//
//}
