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

import uk.gov.hmrc.play.test.UnitSpec

class DocumentStateSpec extends UnitSpec {

  "DocumentState" should {

    "behave correctly" in {
      val state = new DocumentState()

      state.getCurrentPageNumber shouldEqual 0
      state.getNextPageNumber shouldEqual 1
      state.getCurrentPageNumber shouldEqual 1

      state.addPage("Test page 1")
      state.registerContentsSection("Test section 1") shouldEqual true

      state.getNextPageNumber shouldEqual 2
      state.addPage("Test page 2")
      state.registerContentsSection("Test section 1") shouldEqual false

      state.getNextPageNumber shouldEqual 3
      state.addPage("Test page 3")
      state.registerContentsSection("Test section 2") shouldEqual true

      state.getContents shouldEqual Seq(
        "Test section 1" -> 1,
        "Test section 2" -> 3
      )

      state.getPageNumber("Test page 1") shouldEqual 1
      state.getPageNumber("Test page 2") shouldEqual 2
      state.getPageNumber("Unknown page") shouldEqual -1
    }

  }

}
