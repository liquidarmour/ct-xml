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

import uk.gov.hmrc.ct.ct600a.v2.{Loan, WriteOff}

case class CommonTableRow(nameOfParticipator: String,
                          amountRepaid: Option[Int],
                          amountReleasedOrWrittenOff: Option[Int],
                          dateOfRepaymentReleaseOrWriteOff: String)

object CommonTableRow {
  def apply(w: WriteOff, l: Loan): CommonTableRow = {
    val writeOffDate = w.dateWrittenOff.toString("dd/MM/yyyy")
    val amountWrittenOff: Option[Int] = Some(w.amountWrittenOff)
    new CommonTableRow(l.name, None, amountWrittenOff, writeOffDate)
  }

  def apply(l: Loan): CommonTableRow = {
    new CommonTableRow(l.name, l.totalAmountRepaid, None, l.lastRepaymentDate.map(d => d.toString("dd/MM/yyyy")).getOrElse(""))
  }

  def sort(rows: Seq[CommonTableRow]): Seq[CommonTableRow] = rows.sortBy{r => (r.nameOfParticipator, r.amountReleasedOrWrittenOff.getOrElse(0))}

}
