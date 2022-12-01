package io.redgreen.tumbleweed.vcs

import com.google.common.truth.Truth.assertThat
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.jupiter.api.Test

class BlameLineTest {
  @Test
  fun `it should parse a blame line`() {
    // given
    val rawBlameLine =
      "42e7f2b2e8 (<vinay@obvious.in>                                2020-05-28 09:57:16 +0530   3) import android.annotation.SuppressLint"

    // when
    val line = BlameLine.from(rawBlameLine)

    // then
    val localDateTime = LocalDateTime.of(2020, Month.MAY, 28, 9, 57, 16)
    val zoneId = ZoneId.of("+05:30")

    assertThat(line)
      .isEqualTo(
        BlameLine(
          CommitHash("42e7f2b2e8"),
          ZonedDateTime.of(localDateTime, zoneId),
          Email("vinay@obvious.in"),
          3,
          "import android.annotation.SuppressLint"
        )
      )
  }
}
