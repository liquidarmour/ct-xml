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

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class DocumentState {
  private val contents = mutable.LinkedHashMap[String, Integer]()
  private var pageNumber = 0
  private val pages = ListBuffer[(String, Integer)]()

  def addPage(name: String): Unit = pages.append((name, getCurrentPageNumber))

  def getContents: Seq[(String, Integer)] = contents.toSeq

  def getCurrentPageNumber: Int = pageNumber

  def getNextPageNumber: Int = {
    pageNumber += 1
    pageNumber
  }

  def getPageNumber(name: String): Integer = pages.find(_._1 == name).map(_._2).getOrElse(-1)

  def registerContentsSection(name: String): Boolean = {
    if (!contents.contains(name)) {
      contents.put(name, getCurrentPageNumber)
      true
    } else false
  }
}
