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

package uk.gov.hmrc.ct.xml.helpers.accounts.frs105

import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever

import scala.collection.mutable.ListBuffer

object AccountsRendering {

  object NoteType extends Enumeration {
    val CommitmentsByWayOfGuarantee,
    AdvancesAndCredits = Value
  }

  def getVisibleNotes(implicit boxRetriever: Frs105AccountsBoxRetriever): List[NoteType.Value] = {
    import NoteType._

    val visibleNotes = ListBuffer.empty[NoteType.Value]

    if (isCommitmentByWayOfGuaranteeNoteVisible(boxRetriever))
      visibleNotes += CommitmentsByWayOfGuarantee
    if (isAdvancesAndCreditNoteVisible(boxRetriever))
      visibleNotes += AdvancesAndCredits

    visibleNotes.toList
  }

  def isNoteVisible(noteType: NoteType.Value, boxRetriever: Frs105AccountsBoxRetriever): Boolean = {
    getVisibleNotes(boxRetriever).contains(noteType)
  }

  def getNoteNumber(noteType: NoteType.Value, boxRetriever: Frs105AccountsBoxRetriever): Int = {
    val visibleNotes = getVisibleNotes(boxRetriever)

    if (visibleNotes.contains(noteType))
      visibleNotes.indexOf(noteType) + 1
    else
      0
  }

  def getNumbersForNotes(noteTypes: Seq[NoteType.Value], boxRetriever: Frs105AccountsBoxRetriever): Seq[Int] = {
    noteTypes.filter(isNoteVisible(_, boxRetriever)).map(getNoteNumber(_, boxRetriever))
  }

  private def isCommitmentByWayOfGuaranteeNoteVisible(boxRetriever: Frs105AccountsBoxRetriever): Boolean = {
    boxRetriever.ac7991().isTrue
  }

  private def isAdvancesAndCreditNoteVisible(boxRetriever: Frs105AccountsBoxRetriever): Boolean = {
    boxRetriever.ac7992().isTrue
  }
}
