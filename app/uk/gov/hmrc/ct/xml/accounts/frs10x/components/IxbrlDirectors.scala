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

package uk.gov.hmrc.ct.xml.accounts.frs10x.components

import uk.gov.hmrc.ct.xml.RetrieverTypeConverter
import uk.gov.hmrc.ct.accounts.approval.boxes.AccountsApproval
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xAccountsBoxRetriever, Frs10xDirectorsBoxRetriever}

case class IxbrlDirector(name: String, index: Int, inDirectorsReport: Boolean, approver: Boolean, loanSubject: Boolean)

case class IxbrlDirectors(namesInReport: Seq[String], approversNames: Seq[String], namesHavingLoans: Seq[String]) {

  val masterList: List[String] = (namesInReport ++ approversNames ++ namesHavingLoans).toList.distinct

  def director(name: String): IxbrlDirector = {
    IxbrlDirector(
      name = name,
      index = masterList.indexOf(name) + 1,
      inDirectorsReport = namesInReport.contains(name),
      approver = approversNames.contains(name),
      loanSubject = namesHavingLoans.contains(name)
    )
  }

  def director(name: Option[String]): IxbrlDirector = {
    val theName = name.getOrElse(throw new IllegalStateException("Director's name not found"))

    director(theName)
  }
}

object IxbrlDirectors {
  def apply(boxRetriever: Frs10xAccountsBoxRetriever, accountsApproval: AccountsApproval): IxbrlDirectors = {
    val directorsRetriever = RetrieverTypeConverter.retriever[Frs10xDirectorsBoxRetriever](boxRetriever)

    val allDirectorNames = directorsRetriever.directors.directors.map(_.ac8001)
    val allApproverNames = accountsApproval.ac199A.map(_.value) ++ accountsApproval.ac8092.flatMap(_.value)
    val allLoanedNames = boxRetriever match {
      case retriever:Frs102AccountsBoxRetriever => retriever.loansToDirectors().loans.map(_.ac304A).flatMap(_.value)
      case _ => Seq.empty
    }

    new IxbrlDirectors(
      allDirectorNames,
      allApproverNames,
      allLoanedNames
    )
  }
}
