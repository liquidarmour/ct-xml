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

package uk.gov.hmrc.ct.xml.model.submit.hmrc

import org.joda.time.{DateTime, LocalDate}
import uk.gov.hmrc.ct.box.{CtTypeConverters, CtValue}
import uk.gov.hmrc.ct.xml.retrievers.BaseBoxRetriever

class ComputationsModel(private val retriever: BaseBoxRetriever) extends CtTypeConverters {

  private val companyFilingModel: CompanyFilingModel = ??? //CompanyFilingModelGenerator.create(retriever.filing)

  def number[T](implicit m: Manifest[T]): Option[Int] = {
    findCtValue.flatMap( es1 => es1._2.asBoxString.map(_.toInt))
  }

  def boolean[T](implicit m: Manifest[T]): Option[Boolean] = {
    findCtValue.flatMap( es1 => es1._2.asBoxString.map(_.toBoolean))
  }

  def localDate[T](implicit m: Manifest[T]): Option[LocalDate] = {
    findCtValue.flatMap( es1 => es1._2.asBoxString.map(LocalDate.parse))
  }

  private def findCtValue[T](implicit m: Manifest[T]): Option[(String, CtValue[_])] = {
    val simpleName = m.runtimeClass.getSimpleName
    retriever.generateValues.find(es => es._1 == simpleName)
  }

  def periodOfAccountsStartDate: LocalDate = companyFilingModel.periodOfAccounts.startDate

  def periodOfAccountsEndDate: LocalDate = companyFilingModel.periodOfAccounts.endDate

  def hmrcSubmissionDate: Option[DateTime] = ???
//    retriever.filing.submissions.filter(s => s.department == Hmrc && s.correlationId.isDefined)
//    .map(s => s.dateSubmittedLondonTimezone)
//    .headOption

  def isDraft: Boolean = hmrcSubmissionDate.isEmpty
}
