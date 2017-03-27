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

// Only uncomment when
//import java.awt.{Canvas, Font}

object TextHelpers {

// This code is commented out cos it's only for generating font size map for this class.
// Do not use it in production as it requires Java AWT ... which ain't great
//  private def generateFontSizeMap(fontName: String, fontSize: Int): String = {
//
//    val font = new Font(fontName, Font.PLAIN, fontSize)
//    val canvas = new Canvas()
//    val fontMetrics = canvas.getFontMetrics(font)
//    val letters = Seq(
//      "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",	"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
//      "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
//      "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ",
//      "!", "\"", "£", "$", "%", "^", "&", "*", "(", ")", "_", "+", "-", "=", "[", "]", "{", "}", ";", "'", ":", "@", "#", "~", ",", ".", "/", "<", ">", "?", "\\", "|"
//    )
//    val letterToWidthMap = letters.map(letter => {
//      val width = fontMetrics.stringWidth(letter)
//      val escapedLetter = letter.replace("\\", "\\\\").replace("\"", "\\\"")
//      s""""$escapedLetter" -> $width"""
//    }).mkString(", ")
//
//    s"Map($letterToWidthMap)"
//  }

  // This font size map was generated for "Times New Roman" @ 10px
  // And who doesn't like magic numbers!?
  private val fontSizeMap: Map[String, Int] = Map(
    "A" -> 7, "B" -> 7, "C" -> 7, "D" -> 7, "E" -> 6, "F" -> 6, "G" -> 7, "H" -> 7, "I" -> 3, "J" -> 4, "K" -> 7, "L" -> 6, "M" -> 9, "N" -> 7, "O" -> 7, "P" -> 6, "Q" -> 7, "R" -> 7, "S" -> 6, "T" -> 6, "U" -> 7, "V" -> 7, "W" -> 9, "X" -> 7, "Y" -> 7, "Z" -> 6,
    "a" -> 4, "b" -> 5, "c" -> 4, "d" -> 5, "e" -> 4, "f" -> 3, "g" -> 5, "h" -> 5, "i" -> 3, "j" -> 3, "k" -> 5, "l" -> 3, "m" -> 8, "n" -> 5, "o" -> 5, "p" -> 5, "q" -> 5, "r" -> 3, "s" -> 4, "t" -> 3, "u" -> 5, "v" -> 5, "w" -> 7, "x" -> 5, "y" -> 5, "z" -> 4,
    "1" -> 5, "2" -> 5, "3" -> 5, "4" -> 5, "5" -> 5, "6" -> 5, "7" -> 5, "8" -> 5, "9" -> 5, "0" -> 5, " " -> 3,
    "!" -> 3, "\"" -> 4, "£" -> 5, "$" -> 5, "%" -> 8, "^" -> 5, "&" -> 8, "*" -> 5, "(" -> 3, ")" -> 3, "_" -> 5, "+" -> 6, "-" -> 3, "=" -> 6, "[" -> 3, "]" -> 3, "{" -> 5, "}" -> 5, ";" -> 3, "'" -> 2, ":" -> 3, "@" -> 9, "#" -> 5, "~" -> 5, "," -> 3, "." -> 3, "/" -> 3, "<" -> 6, ">" -> 6, "?" -> 4, "\\" -> 3, "|" -> 2
  )

  private val originalFontSize: Float = 10.0f

  private def estimateWordWidth(fontSize: Int, word: String): Int = {
    word.toCharArray.map(char => {
      val charSize =
        if (fontSizeMap.contains(char.toString))
          fontSizeMap(char.toString)
        else
          fontSizeMap("M")

      Math.ceil(charSize * (fontSize / originalFontSize)).toInt
    }).sum
  }

  def measureTextHeight(fontSize: Int, text: String, maxWidth: Int): Int = {

    val words = text.replaceAll("[\r\t]", "").split("[ \n]")

    var linesCount = 1
    var lineWidth = 0

    for (word <- words) {
      val wordWidth = estimateWordWidth(fontSize, word)
      if (wordWidth + lineWidth > maxWidth) {
        linesCount += 1
        lineWidth = wordWidth
      } else {
        lineWidth += wordWidth
      }
    }

    linesCount

  }

}
