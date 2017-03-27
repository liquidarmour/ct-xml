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

object FilingStatusDepartment {
  val hmrc = "HMRC"
  val ch = "CH"
}

sealed trait AccountsType {
  def stringCode: String
  def isStatutory: Boolean
  def isMicro: Boolean
  def hasNotes: Boolean
}

trait MicroAccounts extends AccountsType {
  override val isMicro = true
  override val isStatutory = false
}

trait StatutoryAccounts extends AccountsType {
  override val isMicro = false
  override val isStatutory = true
  override val hasNotes = true
}

case object UploadedAccounts extends StatutoryAccounts{
  val stringCode = "UploadedAccounts"
  override val hasNotes = false
  override val isMicro = false
  override val isStatutory = false
}

case object AbbreviatedStatutoryAccounts extends StatutoryAccounts {
  val stringCode = "AbbreviatedStatutoryAccounts"
}

case object AbridgedAccounts extends StatutoryAccounts {
  val stringCode = "AbridgedAccounts"
}

case object FullStatutoryAccounts extends StatutoryAccounts {
  val stringCode = "FullStatutoryAccounts"
}

case object AbridgedMicroAccounts extends MicroAccounts {
  val stringCode = "AbridgedMicroAccounts"
  override val hasNotes = false
}

case object FullMicroAccounts extends MicroAccounts {
  val stringCode = "FullMicroAccounts"
  override val hasNotes = true
}
