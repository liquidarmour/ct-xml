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

case class Section3(section3Table: Section3Table, a8: String, a9: String, a10: String, a11: String)

//object Section3 {
//  def apply(boxValueRetriever: CT600ABoxRetriever): Section3 = {
//    Section3(
//      section3Table = Section3Table(boxValueRetriever),
//      a8 = boxValueRetriever.a8().value.map(_.toString).getOrElse(""),
//      a9 = boxValueRetriever.a9().value.map(_.toString).getOrElse(""),
//      a10 = boxValueRetriever.a10().value.map(_.toString).getOrElse(""),
//      a11 = boxValueRetriever.a11().value.to2DecimalPlaces
//    )
//  }
//}
