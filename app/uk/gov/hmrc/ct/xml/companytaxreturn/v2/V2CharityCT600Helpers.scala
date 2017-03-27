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

import uk.gov.hmrc.ct.xml.retrievers.V2CharityCT600BoxRetriever

case class V2CharityCT600Helpers(boxRetriever: V2CharityCT600BoxRetriever) extends V2CT600Helper[V2CharityCT600BoxRetriever] {
  override def marginalRateRelief: Option[BigDecimal] = None
}
