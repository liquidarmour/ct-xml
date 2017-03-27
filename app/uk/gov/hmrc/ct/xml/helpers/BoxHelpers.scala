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

import uk.gov.hmrc.ct.xml.model.filing.JsonEncryption._
import uk.gov.hmrc.ct.box._
import NumberHelper._

object BoxHelpers {

  implicit def boxToBoolean(box: CtBoolean): Boolean ={
    box.value
  }

  implicit def boxToBoolean(box: CtOptionalBoolean): Boolean = box.value.contains(true)

  implicit def boxToOptionalBigDecimal(box: CtOptionalInteger): Option[BigDecimal] = {
    box.value.map(i => BigDecimal(i))
  }

  implicit def boxToOptionalBigDecimal(box: CtOptionalBigDecimal): Option[BigDecimal] = {
    box.value
  }

  def boxToPoundsPence(box: CtValue[_]): Option[String] = {
    box match {
      case bd: CtOptionalBigDecimal => bd.value.map(poundsPence)
      case bd: CtOptionalInteger => bd.value.map(poundsPence)
    }
  }

  def boxToDecryptedString(box: CtValue[_]): String = {
    box match {
      case b: CtOptionalString => b.value.getOrElse("").decrypt
      case b: CtString => b.value.decrypt
      case _ => throw new IllegalArgumentException(s"Can only decrypt a string: $box")
    }
  }

  def expectedString(box: Option[String]): String = {
    box.getOrElse(throw new IllegalStateException("Expected string for IXbrl Rendering not present"))
  }

  def expectedInt(box: Option[Int]): Int = {
    box.getOrElse(throw new IllegalStateException("Expected int for IXbrl Rendering not present"))
  }

  def currency(box: CtBigDecimal): String = {
    poundsPence(box.value)
  }

  def currency(box: CtInteger): String = {
    poundsPence(box.value)
  }

  def currency(box: CtOptionalBigDecimal): String = {
    poundsPence(box.orZero)
  }

  def currency(box: CtOptionalInteger): String = {
    poundsPence(box.orZero)
  }
}
