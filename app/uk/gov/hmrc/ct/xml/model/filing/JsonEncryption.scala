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

import uk.gov.hmrc.crypto.{ApplicationCrypto, Crypted, PlainText}

object JsonEncryption {

  private implicit val crypto = ApplicationCrypto.JsonCrypto

  def decrypt(value:String):String = crypto.decrypt(Crypted(value)).value

  def encrypt(value:String):String = crypto.encrypt(PlainText(value)).value

  implicit class RichEncryptedString(value:String){
    def decrypt:String = JsonEncryption.decrypt(value)
    def encrypt:String = JsonEncryption.encrypt(value)
  }
}
