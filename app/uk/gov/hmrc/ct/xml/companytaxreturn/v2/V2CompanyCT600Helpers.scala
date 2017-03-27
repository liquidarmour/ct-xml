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

package uk.gov.hmrc.ct.xml.companytaxreturn.v2

import uk.gov.hmrc.ct.xml.model.submit.hmrc.LoanWriteOffWrapper
import uk.gov.hmrc.ct.xml.helpers.BoxHelpers._
import uk.gov.hmrc.ct.ct600a.v2.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.ct600a.v2.{Loan, WriteOff}
import uk.gov.hmrc.ct.xml.retrievers.V2BaseCT600BoxRetriever

case class V2CompanyCT600Helpers(boxRetriever: V2BaseCT600BoxRetriever) extends V2CT600Helper[V2BaseCT600BoxRetriever] {

  val claimingSmallCompaniesRate: Boolean = boxRetriever.b42().value && boxRetriever.b37().isPositive

  val shouldShowLoansByCloseCompaniesSection: Boolean = {
    boxRetriever match {
      case retriever: CT600ABoxRetriever =>
        retriever.a1.orFalse ||
        retriever.a2.isPositive ||
        retriever.a4.isPositive ||
        retriever.a5.isPositive ||
        retriever.a8.isPositive ||
        retriever.a9.isPositive ||
        retriever.a12.isPositive
      case _ => false
    }
  }

  val beforeEndPeriod = boxRetriever match {
    case retriever: CT600ABoxRetriever =>
      if (retriever.a1) "yes" else "no"
    case _ => ""
  }

  val marginalRateRelief: Option[BigDecimal] = if(boxRetriever.b42 && boxRetriever.b64.isPositive) Some(boxRetriever.b64.value) else None

  val loans: List[Loan] = {
    boxRetriever match {
      case retriever: CT600ABoxRetriever =>
        retriever.lp02.value.getOrElse(List())
      case _ => List.empty
    }
  }


  val getLoanWriteOffWrappers: List[LoanWriteOffWrapper] = {
    boxRetriever match {
      case retriever: CT600ABoxRetriever =>
        val writeOffs: List[WriteOff] = retriever.lp03.value.getOrElse(List.empty)

        for {
          writeOff <- writeOffs
          loan <- loans.find(loan => loan.id.equals(writeOff.loanId))
        } yield {
          LoanWriteOffWrapper(loan, writeOff)
        }
      case _ => List.empty[LoanWriteOffWrapper]
    }
  }

  def writeOffsLaterReliefNow: List[LoanWriteOffWrapper] = {
    boxRetriever match {
      case retriever: CT600ABoxRetriever =>
        getLoanWriteOffWrappers.filter(_.writeOff.isLaterReliefNowDue(retriever.cp2().value, retriever.lpq07()))
      case _ => List.empty
    }
  }

  def writeOffsReliefEarlierThan: List[LoanWriteOffWrapper] = getLoanWriteOffWrappers.filter(_.writeOff.isReliefEarlierThanDue(boxRetriever.cp2.value))

  def shouldShowLoanLaterReliefNow: Boolean = {
    boxRetriever match {
      case retriever: CT600ABoxRetriever =>
        retriever.a8.isPositive || retriever.a9.isPositive
      case _ => false
    }
  }

  def repaymentsLaterReliefNow: List[Loan] = {
    boxRetriever match {
      case retriever: CT600ABoxRetriever =>
        loans.filter(_.isRepaymentLaterReliefNowDue(boxRetriever.cp2().value, retriever.lpq07()))
      case _ => List.empty
    }
  }


  def hasSupplementaryPages: Boolean = {
    taq01 || isCharity || shouldShowLoansByCloseCompaniesSection
  }
}
