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

case class Section2(section2Table: Section2Table, a4: String, a5: String, a6: String, a7: String)

//object Section2 {
//  def apply(boxValueRetriever: CT600ABoxRetriever): Section2 = {
//    Section2(
//      section2Table = Section2Table(boxValueRetriever),
//      a4 = boxValueRetriever.a4().value.map(_.toString).getOrElse(""),
//      a5 = boxValueRetriever.a5().value.map(_.toString).getOrElse(""),
//      a6 = boxValueRetriever.a6().value.map(_.toString).getOrElse(""),
//      a7 = boxValueRetriever.a7().value.to2DecimalPlaces
//    )
//  }
//}
