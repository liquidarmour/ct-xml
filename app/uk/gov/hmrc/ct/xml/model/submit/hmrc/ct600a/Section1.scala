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

case class Section1(section1Table: Section1Table, a1: Boolean, a2: String, a3: String)

//object Section1 {
//  def apply(boxValueRetriever: CT600ABoxRetriever): Section1 = {
//    Section1(
//      section1Table = Section1Table(boxValueRetriever),
//      a1 = boxValueRetriever.a1().value.getOrElse(false),
//      a2 = boxValueRetriever.a2().value.map(_.toString).getOrElse(""),
//      a3 = boxValueRetriever.a3().value.to2DecimalPlaces
//    )
//  }
//}
