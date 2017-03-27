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

package uk.gov.hmrc.ct.xml.helpers

import java.text.NumberFormat
import java.util.Locale

object NumberHelper {

  def pounds(value: BigDecimal): String =
    value.setScale(0, BigDecimal.RoundingMode.HALF_UP).toString

  def pounds(value: Option[BigDecimal]): String =
    value.map(pounds(_)).getOrElse("")

  def poundsPence(value: BigDecimal): String =
    value.setScale(2, BigDecimal.RoundingMode.HALF_UP).toString

  def poundsPence(value: Option[BigDecimal]): String =
    value.map(poundsPence(_)).getOrElse("")
    
  def poundsPence(value: Int): String =
    value + ".00"

  def poundsPence(value: => Option[Int]): String =
    value.map(poundsPence(_)).getOrElse("")
    
  val Zero = BigDecimal(0)

  def commaSeparated(n: Int) = NumberFormat.getNumberInstance(Locale.UK).format(n)

  def formatAsPercent(value: BigDecimal): String =
    (value * 100).toString() + "%"

  def formatAsPercent(value: Option[BigDecimal]): String = {
    if (value.nonEmpty)
      (value.get * 100).toString() + "%"
    else
      ""
  }
}
