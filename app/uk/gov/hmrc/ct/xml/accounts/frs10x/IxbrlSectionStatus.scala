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

package uk.gov.hmrc.ct.xml.accounts.frs10x

import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xAccountsBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.version.CoHoAccounts.{CoHoAbridgedAccounts, CoHoMicroEntityAccounts, CoHoStatutoryAccounts}
import uk.gov.hmrc.ct.version.HmrcReturns.HmrcMicroEntityAccounts
import uk.gov.hmrc.ct.version.ReturnType

case class IxbrlSectionStatus(boxRetriever: Frs10xAccountsBoxRetriever, returnType: ReturnType) {

  def isDirectorsReportEnabled: Boolean = (returnType, boxRetriever) match {
    case (CoHoAbridgedAccounts, br: Frs10xDirectorsBoxRetriever) => retrieveAC8021WithException(br, "CoHo Abridged")
    case (CoHoStatutoryAccounts, br: Frs10xDirectorsBoxRetriever) => retrieveAC8021WithException(br, "CoHo Full")
    case (CoHoMicroEntityAccounts, br: Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever) if br.hmrcFiling().value =>
      retrieveAC8023WithException(br, "CoHo Micro Entity") && retrieveAC8021WithException(br, "CoHo Joint Micro Entity")
    case (CoHoMicroEntityAccounts, br: Frs10xDirectorsBoxRetriever) => retrieveAC8021WithException(br, "CoHo Micro Entity")
    case (HmrcMicroEntityAccounts, br: Frs10xDirectorsBoxRetriever) => retrieveAC8023WithException(br, "HMRC Micro Entity")
    case _ => true
  }

  def retrieveAC8021WithException(br: Frs10xAccountsBoxRetriever with Frs10xDirectorsBoxRetriever, errorMsgAccounts: String): Boolean = {
    br.ac8021().value.getOrElse(throw new IllegalStateException(s"Rendering Directors Report for $errorMsgAccounts without answering AC8021"))
  }

  def retrieveAC8023WithException(br: Frs10xAccountsBoxRetriever with Frs10xDirectorsBoxRetriever, errorMsgAccounts: String): Boolean = {
    br.ac8023().value.getOrElse(throw new IllegalStateException(s"Rendering Directors Report for $errorMsgAccounts without answering AC8023"))
  }

  def isProfitAndLossEnabled: Boolean = (returnType, boxRetriever) match {
    case (CoHoAbridgedAccounts, br: Frs10xFilingQuestionsBoxRetriever) => br.acq8161().value.getOrElse(throw new IllegalStateException("Rendering Profit and Loss for CoHo Abridged without answering ACQ8161"))
    case (CoHoStatutoryAccounts, br: Frs10xFilingQuestionsBoxRetriever) => br.acq8161().value.getOrElse(throw new IllegalStateException("Rendering Profit and Loss for CoHo Abridged without answering ACQ8161"))
    case (CoHoMicroEntityAccounts, br: Frs10xFilingQuestionsBoxRetriever) => br.acq8161().value.getOrElse(throw new IllegalStateException("Rendering Profit and Loss for CoHo Abridged without answering ACQ8161"))

    case _ => true
  }

}
