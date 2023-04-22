package com.legacycode.tumbleweed.vcs.blame

import com.legacycode.tumbleweed.vcs.CommitHash
import com.legacycode.tumbleweed.vcs.Email
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
      "(?<CommitHash>[a-fA-F0-9]+) \\(\\<(?<Email>.+)\\> (?<ZonedDateTime>.+?) (?<LineNumber>\\d+)\\) (?<Content>.*)"
    private val blameLinePattern = Pattern.compile(BLAME_LINE_REGEX)

    private const val BLAME_LINE_REGEX_WITH_FILE_PATH =
      "(?<CommitHash>[a-fA-F0-9]+) (.*)? \\(\\<(?<Email>.+)\\> (?<ZonedDateTime>.+?) (?<LineNumber>\\d+)\\) (?<Content>.*)"
    private val blameLineWithFilePathPattern = Pattern.compile(BLAME_LINE_REGEX_WITH_FILE_PATH)

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

      return BlameLine(
        CommitHash(blameLineMatcher.group("CommitHash")),
        ZonedDateTime.parse(rawZonedDateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
        Email(blameLineMatcher.group("Email")),
        blameLineMatcher.group("LineNumber").toInt(),
        blameLineMatcher.group("Content"),
      )
    }
  }
}
