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

package uk.gov.hmrc.ct.xml.companytaxreturn.v3

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.box.retriever.{BoxRetriever, FilingAttributesBoxValueRetriever}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.{AboutThisReturnBoxRetriever, CT600BoxRetriever, RepaymentsBoxRetriever}
import uk.gov.hmrc.ct.ct600a.v3.LoansToParticipators
import uk.gov.hmrc.ct.ct600a.v3.retriever.CT600ABoxRetriever
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever
import uk.gov.hmrc.ct.xml.RetrieverTypeConverter

import scala.annotation.tailrec

object V3CT600Helper extends RetrieverTypeConverter {

  def stringFromCT600(func: (CT600BoxRetriever) => String): PartialFunction[BoxRetriever, String] = {
    case br: CT600BoxRetriever => func(br)
  }

  def stringFromCT600E(func: (CT600EBoxRetriever) => String): PartialFunction[BoxRetriever, String] = {
    case br: CT600EBoxRetriever => func(br)
  }

  def stringFromRepayments(func: (RepaymentsBoxRetriever) => String): PartialFunction[BoxRetriever, String] = {
    case br: RepaymentsBoxRetriever => func(br)
  }

  def stringFromFilingAttributes(func: (FilingAttributesBoxValueRetriever) => String): PartialFunction[BoxRetriever, String] = {
    case br: FilingAttributesBoxValueRetriever => func(br)
  }

  def intFromCT600(func: (CT600BoxRetriever) => Int): PartialFunction[BoxRetriever, Int] = {
    case br: CT600BoxRetriever => func(br)
  }

  def intFromCT600E(func: (CT600EBoxRetriever) => Int): PartialFunction[BoxRetriever, Int] = {
    case br: CT600EBoxRetriever => func(br)
  }

  def dateFromCT600(func: (CT600BoxRetriever) => LocalDate): PartialFunction[BoxRetriever, LocalDate] = {
    case br: CT600BoxRetriever => func(br)
  }

  def dateFromCT600E(func: (CT600EBoxRetriever) => LocalDate): PartialFunction[BoxRetriever, LocalDate] = {
    case br: CT600EBoxRetriever => func(br)
  }

  def dateFromComputations(func: (ComputationsBoxRetriever) => LocalDate): PartialFunction[BoxRetriever, LocalDate] = {
    case br: ComputationsBoxRetriever => func(br)
  }

  def booleanFromCT600(func: (CT600BoxRetriever) => Boolean): PartialFunction[BoxRetriever, Boolean] = {
    case br: CT600BoxRetriever => func(br)
  }

  def booleanFromCT600E(func: (CT600EBoxRetriever) => Boolean): PartialFunction[BoxRetriever, Boolean] = {
    case br: CT600EBoxRetriever => func(br)
  }

  def booleanFromAboutThisReturn(func: (AboutThisReturnBoxRetriever) => Boolean): PartialFunction[BoxRetriever, Boolean] = {
    case br: AboutThisReturnBoxRetriever => func(br)
  }

  def decimalFromCT600(func: (CT600BoxRetriever) => BigDecimal): PartialFunction[BoxRetriever, BigDecimal] = {
    case br: CT600BoxRetriever => func(br)
  }

  def decimalFromCT600E(func: (CT600EBoxRetriever) => BigDecimal): PartialFunction[BoxRetriever, BigDecimal] = {
    case br: CT600EBoxRetriever => func(br)
  }

  def optionFromCT600[O](func: (CT600BoxRetriever) => Option[O]): PartialFunction[BoxRetriever, Option[O]] = {
    case br: CT600BoxRetriever => func(br)
  }

  def optionFromRepayments[O](func: (RepaymentsBoxRetriever) => Option[O]): PartialFunction[BoxRetriever, Option[O]] = {
    case br: RepaymentsBoxRetriever => func(br)
  }

  def optionFromAboutThisReturn[O](func: (AboutThisReturnBoxRetriever) => Option[O]): PartialFunction[BoxRetriever, Option[O]] = {
    case br: AboutThisReturnBoxRetriever => func(br)
  }

  def optionFromCT600A[O](func: (CT600ABoxRetriever) => Option[O]): PartialFunction[BoxRetriever, Option[O]] = {
    case br: CT600ABoxRetriever => func(br)
  }

  def optionFromCT600J[O](func: (CT600JBoxRetriever) => Option[O]): PartialFunction[BoxRetriever, Option[O]] = {
    case br: CT600JBoxRetriever => func(br)
  }

  def optionFromCT600E[O](func: (CT600EBoxRetriever) => Option[O]): PartialFunction[BoxRetriever, Option[O]] = {
    case br: CT600EBoxRetriever => func(br)
  }

  def loansToParticipatorsFromCT600A(func: (CT600ABoxRetriever) => Option[LoansToParticipators]): PartialFunction[BoxRetriever, Option[LoansToParticipators]] = {
    case br: CT600ABoxRetriever => func(br)
  }

  @tailrec
  private def buildFunctions[T](base: PartialFunction[BoxRetriever, T], functions: PartialFunction[BoxRetriever, T]*): PartialFunction[BoxRetriever, T] = {
    functions.toList match {
      case Nil => base
      case head :: tail => buildFunctions(base orElse head, tail.toArray: _*)
      case head :: Nil => base orElse head
    }
  }

  def stringValue(boxRetriever: BoxRetriever, functions: PartialFunction[BoxRetriever, String]*): String = {
    buildFunctions(base, functions.toArray: _*)(boxRetriever)
  }

  def dateValue(boxRetriever: BoxRetriever, functions: PartialFunction[BoxRetriever, LocalDate]*): LocalDate = {
    buildFunctions(base, functions.toArray: _*)(boxRetriever)
  }

  def booleanValue(boxRetriever: BoxRetriever, functions: PartialFunction[BoxRetriever, Boolean]*): Boolean = {
    buildFunctions(base, functions.toArray: _*)(boxRetriever)
  }

  def intValue(boxRetriever: BoxRetriever, functions: PartialFunction[BoxRetriever, Int]*): Int = {
    buildFunctions(base, functions.toArray: _*)(boxRetriever)
  }

  def decimalValue(boxRetriever: BoxRetriever, functions: PartialFunction[BoxRetriever, BigDecimal]*): BigDecimal = {
    buildFunctions(base, functions.toArray: _*)(boxRetriever)
  }

  def optional[O](boxRetriever: BoxRetriever, functions: PartialFunction[BoxRetriever, Option[O]]*): Option[O] = {
    val funcs = functions.toList :+ none[O]()
    buildFunctions(base, funcs.toArray: _*)(boxRetriever)
  }

  def loansToParticipatorsOptional(boxRetriever: BoxRetriever, functions: PartialFunction[BoxRetriever, Option[LoansToParticipators]]*): Option[LoansToParticipators] = {
    buildFunctions(base, functions.toArray: _*)(boxRetriever)
  }

  def defaultValue[T](defaultVal: T): PartialFunction[BoxRetriever, T] = {
    case _ => defaultVal
  }

  def none[T](): PartialFunction[BoxRetriever, Option[T]] = {
    case _ => None
  }

  def base[T]: PartialFunction[BoxRetriever, T] = new PartialFunction[BoxRetriever, T] {
    def apply(d: BoxRetriever): T = throw new IllegalStateException("")
    def isDefinedAt(d: BoxRetriever) = false
  }

}
