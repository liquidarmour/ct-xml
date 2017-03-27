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

package uk.gov.hmrc.ct.xml.model.filing

import org.joda.time.LocalDate
import play.api.i18n.Messages
import uk.gov.hmrc.ct.accounts.frsse2008.calculations.{PeriodCalculator, PeriodHeadingComponents}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

trait AccountsPeriodHeadingCalculator {

  def periodHeading(startDate: LocalDate, endDate: LocalDate): (String, String) = {
    PeriodCalculator.periodHeadingComponents(startDate, endDate) match {
      case PeriodHeadingComponents(12, messageKey, dateText) => ("",dateText)
      case PeriodHeadingComponents(monthCount, messageKey, dateText) => (monthCount + " " + Messages(messageKey), dateText)
    }
  }

  def periodHeading(startDate: Option[LocalDate], endDate: Option[LocalDate]): Option[(String, String)] = {
    (startDate, endDate) match {
      case (Some(sd), Some(ed)) => Some(periodHeading(sd, ed))
      case _ => None
    }
  }
}

object AccountsPeriodHeadingCalculator extends AccountsPeriodHeadingCalculator
