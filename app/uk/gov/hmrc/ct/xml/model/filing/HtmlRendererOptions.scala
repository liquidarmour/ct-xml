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

import play.api.mvc.Request
import scala.collection.mutable.ListBuffer

case class HtmlRendererOptions(print: Option[Boolean] = None,
                               full: Option[Boolean] = None,
                               useScaling: Option[Boolean] = None,
                               isDraft: Option[Boolean] = None,
                               sectionsToHide: Seq[String] = Seq.empty) {

  def toQueryString = {
    val queryStringParams = new ListBuffer[String]()

    if (print.nonEmpty)
      queryStringParams += s"print=${print.get}"

    if (full.nonEmpty)
      queryStringParams += s"full=${full.get}"

    if (useScaling.nonEmpty)
      queryStringParams += s"use-scaling=${useScaling.get}"

    if (isDraft.nonEmpty)
      queryStringParams += s"draft=${isDraft.get}"

    "?" + (queryStringParams ++ sectionsToHide.map(value => s"sections-to-hide=$value")).mkString("&")
  }

  def withDraft(value: Option[Boolean]) = copy(isDraft = value)

}

object HtmlRendererOptions {

  def apply(request: Request[_]): HtmlRendererOptions = {
    new HtmlRendererOptions(
      print = booleanFromQueryString(request, "print"),
      full = booleanFromQueryString(request, "full"),
      useScaling = booleanFromQueryString(request, "use-scaling"),
      isDraft = booleanFromQueryString(request, "draft"),
      sectionsToHide = request.queryString.getOrElse("sections-to-hide", Seq.empty)
    )
  }

  private def booleanFromQueryString(request: Request[_], name: String): Option[Boolean] = {
    val values = request.queryString.getOrElse(name, Seq.empty)
    if (values.contains("true"))
      Option(true)
    else if (values.contains("false"))
      Option(false)
    else
      None
  }

}

object HtmlRendererSections {

  val Index = "index"
  val Attachments = "attachments"
  val Accounts = "accounts"
  val Computations = "computations"

}
