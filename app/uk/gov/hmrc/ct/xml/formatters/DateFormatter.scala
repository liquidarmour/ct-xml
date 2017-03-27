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

package uk.gov.hmrc.ct.xml.formatters

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import uk.gov.hmrc.ct.xml.formatters.DateFormat.DateFormat

object DateFormat extends Enumeration {

  val LongUK = Value("dd MMMM yyyy")
  val FriendlyUK = Value("d MMM yyyy")
  val LongWithShortDayUK = Value("d MMMM yyyy")
  val XML = Value("yyyy-MM-dd")
  val XML_DOT_NOTATION = Value("d.M.yy")
  val Year = Value("yyyy")
  val DDMMYYYY = Value("dd/MM/yyyy")
  type DateFormat = Value

}

object DateFormatter {

  def formatOption(date: Option[LocalDate], formatType: DateFormat) : String = date.fold("")(_.toString(formatType.toString))

  def format(date: LocalDate, formatType: DateFormat) : String = date.toString(formatType.toString)

  def formatXml(date: LocalDate): String = date.toString(DateFormat.XML.toString)

  def formatXmlDotNotation(date: LocalDate): String = date.toString(DateFormat.XML_DOT_NOTATION.toString)

  def parse(dateString: String, formatType: DateFormat): LocalDate = LocalDate.parse(dateString, DateTimeFormat.forPattern(formatType.toString))
}
