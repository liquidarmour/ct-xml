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

package uk.gov.hmrc.ct.xml.accounts.frs102.notes.balanceSheet

import uk.gov.hmrc.ct.xml.accounts.frs10x.components.xml.{labelAndDisplayAssetsNoteFigure, labelAndDisplayDebitNoteFigure}
import uk.gov.hmrc.ct.box.{CtOptionalInteger, Debit}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

object FiguresHelper {

  def labelAndDisplayFigures(id: String,
                             label: String,
                             name: String,
                             box: CtOptionalInteger,
                             contextPrefix: String,
                             isTotal: Boolean = false,
                             isGrandTotal: Boolean = false,
                             bracketsIfNegative: Boolean = false) = {
    box match {
      case b: Debit =>
        labelAndDisplayDebitNoteFigure(id = s"$id-curr-val",
                                       value = b.value.map(BigDecimal(_)),
                                       name = name,
                                       context = contextPrefix,
                                       isTotal = isTotal,
                                       isGrandTotal = isGrandTotal)
      case _ =>
        labelAndDisplayAssetsNoteFigure(id = s"$id-curr-val",
                                        value = box.value.map(BigDecimal(_)),
                                        name = name,
                                        context = contextPrefix,
                                        isTotal = isTotal,
                                        isGrandTotal = isGrandTotal,
                                        bracketsIfNegative = bracketsIfNegative)
    }
  }


}
