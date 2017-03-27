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

package uk.gov.hmrc.ct.xml.retrievers

import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.ct600.retriever.DeclarationBoxRetriever
import uk.gov.hmrc.ct.ct600.v2.retriever.{RepaymentsBoxRetriever, ReturnStatementsBoxRetriever}
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.ct600j.v2.retriever.CT600JBoxRetriever


trait V2CharityCT600BoxRetriever extends BaseBoxRetriever
                                         with CT600EBoxRetriever
                                         with RepaymentsBoxRetriever
                                         with DeclarationBoxRetriever
                                         with AttachmentsRetriever
                                         with ReturnStatementsBoxRetriever
                                         with CT600JBoxRetriever
                                         with Frsse2008AccountsBoxRetriever
