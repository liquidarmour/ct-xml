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

import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.ct600a.v2.{Loan, WriteOff}

trait CommonTableMaker {

  def getAllRows(boxValueRetriever: CT600ABoxRetriever): Seq[CommonTableRow] = {
    val rows = getLoanRepaymentRows(boxValueRetriever) ++ getWriteOffRows(boxValueRetriever)
    CommonTableRow.sort(rows)
  }

  def getLoanRepaymentRows(boxValueRetriever: CT600ABoxRetriever): Seq[CommonTableRow]

  def getWriteOffRows(boxValueRetriever: CT600ABoxRetriever): Seq[CommonTableRow]

  def getLoansAndFilter(boxValueRetriever: CT600ABoxRetriever)(filterFunc: Loan => Boolean): Seq[CommonTableRow] = {
    getLoans(boxValueRetriever).filter(filterFunc).map(CommonTableRow(_))
  }

  def getWriteOffsAndFilter(boxValueRetriever: CT600ABoxRetriever)(filterFunc: PartialFunction[(WriteOff, Loan), Boolean]): Seq[CommonTableRow] = {
    getWriteOffs(boxValueRetriever).filter(filterFunc).map { case (w, l) => CommonTableRow(w, l) }
  }

   def getLoans(boxValueRetriever: CT600ABoxRetriever): Seq[Loan] = {
    boxValueRetriever.lp02().loans.getOrElse(Nil)
  }

   def getWriteOffs(boxValueRetriever: CT600ABoxRetriever): Seq[(WriteOff, Loan)] = {
    val writeOffs = boxValueRetriever.lp03().value.getOrElse(Nil)
    val loans = getLoans(boxValueRetriever)
    writeOffs
      .map { w => (w, loans.find(l => l.id == w.loanId)) }
      .map { case (w, optLoan) => (w, optLoan.getOrElse(throw new IllegalStateException("Every WriteOff should have an associated Loan"))) }
  }
}
