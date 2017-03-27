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

package uk.gov.hmrc.ct.xml.helpers.accounts

import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever, FullAccountsBoxRetriever}
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.domain.CompanyTypes.{CompanyLimitedByGuarantee, CompanyType, LimitedByGuaranteeCASC, LimitedByGuaranteeCharity}
import uk.gov.hmrc.ct.xml.helpers.accounts.xml.{debitWrapper, negativeWrapper}

import scala.collection.mutable.ListBuffer

object AccountsRendering {

  object NoteType extends Enumeration {
    val IntangibleAssets,
        TangibleAssets,
        Debtors,
        CreditorsWithinOneYear,
        CreditorsAfterOneYear,
        RevaluationReserve,
        ProfitBeforeTax,
        AccountingPolicies,
        Dividends,
        Employees,
        FinancialCommitments,
        LoansToDirectors,
        ChangesInPresentationAndPriorPeriodAdjustments,
        RelatedPartyTransactions,
        PostBalanceSheetEvents = Value
  }

  def negativeWithBrackets(value: Int)(body: play.twirl.api.Xml ): play.twirl.api.Xml = {
    if (value < 0) {
      negativeWrapper(body)
    } else {
      body
    }
  }

  def negativeWithBrackets(value: BigDecimal)(body: play.twirl.api.Xml ): play.twirl.api.Xml = {
    if (value < 0) {
      negativeWrapper(body)
    } else {
      body
    }
  }

  def positiveWithBrackets(value: Int)(body: play.twirl.api.Xml ): play.twirl.api.Xml = {
    if (value >= 0) {
      debitWrapper(body)
    } else {
      body
    }
  }

  def getVisibleNotes(implicit boxRetriever: Frs102AccountsBoxRetriever): List[NoteType.Value] = {
    import NoteType._

    val visibleNotes = ListBuffer.empty[NoteType.Value]

    // Accounting policies is always visible
    visibleNotes += AccountingPolicies

    if (isProfitBeforeTaxNoteVisible(boxRetriever))
      visibleNotes += ProfitBeforeTax
    if (isDividendsNoteVisible(boxRetriever))
      visibleNotes += Dividends
    if (isEmployeesNoteVisible(boxRetriever))
      visibleNotes += Employees
    if (isIntangibleAssetsNoteVisible(boxRetriever))
      visibleNotes += IntangibleAssets
    if (isTangibleAssetsNoteVisible(boxRetriever))
      visibleNotes += TangibleAssets
    if (isDebtorsNoteVisible(boxRetriever))
      visibleNotes += Debtors
    if (isCreditorsWithinOneYearNoteVisible(boxRetriever))
      visibleNotes += CreditorsWithinOneYear
    if (isCreditorsAfterOneYearNoteVisible(boxRetriever))
      visibleNotes += CreditorsAfterOneYear
    if (isRevaluationReserveNoteVisible(boxRetriever))
      visibleNotes += RevaluationReserve
    if (isFinancialCommitmentsNoteVisible(boxRetriever))
      visibleNotes += FinancialCommitments
    if (isLoansToDirectorsNoteVisible(boxRetriever))
      visibleNotes += LoansToDirectors
    if (isChangesInPresentationAndPriorPeriodAdjustmentsNoteVisible(boxRetriever))
      visibleNotes += ChangesInPresentationAndPriorPeriodAdjustments
    if (isRelatedPartyTransactionsNoteVisible(boxRetriever))
      visibleNotes += RelatedPartyTransactions
    if (isPostBalanceSheetEventsNoteVisible(boxRetriever))
      visibleNotes += PostBalanceSheetEvents

    visibleNotes.toList
  }

  def isNoteVisible(noteType: NoteType.Value, boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    getVisibleNotes(boxRetriever).contains(noteType)
  }

  def getNoteNumber(noteType: NoteType.Value, boxRetriever: Frs102AccountsBoxRetriever): Int = {
    val visibleNotes = getVisibleNotes(boxRetriever)

    if (visibleNotes.contains(noteType))
      visibleNotes.indexOf(noteType) + 1
    else
      0
  }

  def getNumbersForNotes(noteTypes: Seq[NoteType.Value], boxRetriever: Frs102AccountsBoxRetriever): Seq[Int] = {
    noteTypes.filter(isNoteVisible(_, boxRetriever)).map(getNoteNumber(_, boxRetriever))
  }

  private def isIntangibleAssetsNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    val values = Seq(
      boxRetriever.ac114().value,
      boxRetriever.ac115().value,
      boxRetriever.ac116().value,
      boxRetriever.ac209().value,
      boxRetriever.ac210().value,
      boxRetriever.ac117().value,
      boxRetriever.ac118().value,
      boxRetriever.ac119().value,
      boxRetriever.ac120().value,
      boxRetriever.ac211().value,
      boxRetriever.ac123().value,
      boxRetriever.ac122().value
    )

    val isNoteNonEmpty = values.exists(_.nonEmpty) || boxRetriever.ac5123().value.getOrElse("").trim().nonEmpty

    boxRetriever.ac42().value.nonEmpty && isNoteNonEmpty
  }

  private def isTangibleAssetsNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.ac44().value.nonEmpty
  }

  private def isDebtorsNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever =>
        x.ac5052A().hasValue ||
          x.ac5052B().hasValue ||
          x.ac5052C().hasValue

      case x: FullAccountsBoxRetriever =>
        x.ac140().hasValue ||
          x.ac141().hasValue ||
          x.ac5052A().hasValue ||
          x.ac5052B().hasValue ||
          x.ac5052C().hasValue
    }
  }

  private def isCreditorsWithinOneYearNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever =>
        x.ac5058A().hasValue

      case x: FullAccountsBoxRetriever =>
        x.ac58().hasValue ||
          x.ac59().hasValue
    }
  }

  private def isCreditorsAfterOneYearNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever match {
      case x: AbridgedAccountsBoxRetriever =>
        x.ac5064A().hasValue

      case x: FullAccountsBoxRetriever =>
        x.ac64().hasValue ||
          x.ac65().hasValue
    }
  }

  private def isRevaluationReserveNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.ac190().value.nonEmpty
  }

  private def isProfitBeforeTaxNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.ac5032().value.nonEmpty
  }

  private def isDividendsNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.ac7200().orFalse
  }

  private def isEmployeesNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.ac7300().orFalse
  }

  private def isFinancialCommitmentsNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.ac7400().orFalse
  }

  private def isLoansToDirectorsNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.loansToDirectors().loans.nonEmpty || boxRetriever.loansToDirectors().ac7501.value.nonEmpty
  }

  private def isChangesInPresentationAndPriorPeriodAdjustmentsNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.ac7600().orFalse
  }

  private def isRelatedPartyTransactionsNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.relatedPartyTransactions().transactions.nonEmpty || boxRetriever.relatedPartyTransactions().ac7806.value.nonEmpty
  }

  private def isPostBalanceSheetEventsNoteVisible(boxRetriever: Frs102AccountsBoxRetriever): Boolean = {
    boxRetriever.ac7900().orFalse
  }

  private val limitedByGuaranteeCompanies: Set[CompanyType] = Set(CompanyLimitedByGuarantee, LimitedByGuaranteeCASC, LimitedByGuaranteeCharity)

  def isLimitedByGuaranteeCompany(boxRetriever: BoxRetriever): Boolean = boxRetriever match {
    case br: FilingAttributesBoxValueRetriever => limitedByGuaranteeCompanies.contains(br.companyType().value)
    case _ => false
  }

  def prependLbgPrefix(messageKey: String, boxRetriever: BoxRetriever): String = if (isLimitedByGuaranteeCompany(boxRetriever)) s"lbg.$messageKey" else messageKey
}
