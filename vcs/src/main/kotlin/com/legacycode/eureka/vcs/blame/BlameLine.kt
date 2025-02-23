package com.legacycode.eureka.vcs.blame

import com.legacycode.eureka.vcs.CommitHash
import com.legacycode.eureka.vcs.Email
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern
import org.slf4j.LoggerFactory

data class BlameLine(
  val commitHash: CommitHash,
  val timestamp: ZonedDateTime,
  val email: Email,
  val number: Int,
  val content: String,
) {
  companion object {
    private const val BLAME_LINE_REGEX =
      "\\^?(?<CommitHash>[a-fA-F0-9]+) \\(\\<(?<Email>.+?)\\> (?<ZonedDateTime>.+?) (?<LineNumber>\\d+)\\)(\\s(?<Content>.*))?"
    private val blameLinePattern = Pattern.compile(BLAME_LINE_REGEX, Pattern.DOTALL)

    private const val BLAME_LINE_REGEX_WITH_FILE_PATH =
      "\\^?(?<CommitHash>[a-fA-F0-9]+) (.+)? \\(\\<(?<Email>.+?)\\> (?<ZonedDateTime>.+?) (?<LineNumber>\\d+)\\)(\\s(?<Content>.*))?"
    private val blameLineWithFilePathPattern = Pattern.compile(BLAME_LINE_REGEX_WITH_FILE_PATH, Pattern.DOTALL)

    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss Z"

    private val logger = LoggerFactory.getLogger(BlameLine::class.java)

    fun from(rawBlameLine: String): BlameLine {
      logger.debug("Parsing raw blame line: {}", rawBlameLine)

      var blameLineMatcher = blameLinePattern.matcher(rawBlameLine)
      if (!blameLineMatcher.matches()) {
        blameLineMatcher = blameLineWithFilePathPattern.matcher(rawBlameLine)
        blameLineMatcher.matches()
      }

      val rawZonedDateTime = blameLineMatcher.group("ZonedDateTime").trim()
      val content = blameLineMatcher.group("Content") ?: ""

      return BlameLine(
        CommitHash(blameLineMatcher.group("CommitHash")),
        ZonedDateTime.parse(rawZonedDateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
        Email(blameLineMatcher.group("Email")),
        blameLineMatcher.group("LineNumber").toInt(),
        content,
      )
    }
  }
}
