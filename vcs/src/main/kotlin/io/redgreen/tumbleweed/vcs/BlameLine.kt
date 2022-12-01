package io.redgreen.tumbleweed.vcs

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

data class BlameLine(
  val commitHash: CommitHash,
  val timestamp: ZonedDateTime,
  val email: Email,
  val number: Int,
  val content: String,
) {
  companion object {
    private const val BLAME_LINE_REGEX =
      "(?<CommitHash>[a-fA-F0-9]+) \\(\\<(?<Email>.+)\\> (?<ZonedDateTime>.+) (?<LineNumber>\\d+)\\) (?<Content>.*)"
    private val blameLinePattern = Pattern.compile(BLAME_LINE_REGEX)

    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss Z"

    fun from(rawBlameLine: String): BlameLine {
      val blameLineMatcher = blameLinePattern.matcher(rawBlameLine)
      blameLineMatcher.matches()

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
