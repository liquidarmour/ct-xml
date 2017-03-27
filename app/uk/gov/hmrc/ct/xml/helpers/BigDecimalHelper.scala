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

import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale


object BigDecimalHelper {
  val Zero = BigDecimal("0")

  def convertToString(value: Option[BigDecimal]) : String = value.fold("")(formatAsString(_))

  def formatAsString(value: BigDecimal, digits: Int = 0): String = {
    val formatter = NumberFormat.getInstance(Locale.UK)
    formatter.setMinimumFractionDigits(digits)
    formatter.setMaximumFractionDigits(digits)
    formatter.setRoundingMode(RoundingMode.HALF_UP)

    formatter.format(value.abs)
  }

  def pounds(value: BigDecimal): BigDecimal =
    value.setScale(0, BigDecimal.RoundingMode.HALF_UP)

  def poundsPence(value: BigDecimal): BigDecimal =
    value.setScale(2, BigDecimal.RoundingMode.HALF_UP)

}
