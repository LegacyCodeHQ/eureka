package com.legacycode.tumbleweed.vcs.blame

import com.google.common.truth.Truth.assertThat
import com.legacycode.tumbleweed.vcs.CommitHash
import com.legacycode.tumbleweed.vcs.Email
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

  @Test
  fun `it should parse a blame line with file path`() {
    // given
    val rawBlameLine =
      "b065618bf2 app/src/main/java/org/simple/clinic/patient/PatientRepository.kt                  (<pratul@uncommon.is>                              2018-06-15 20:02:04 +0530   1) package org.simple.clinic.patient"

    // when
    val line = BlameLine.from(rawBlameLine)

    // then
    val localDateTime = LocalDateTime.of(2018, Month.JUNE, 15, 20, 2, 4)
    val zoneId = ZoneId.of("+05:30")

    assertThat(line)
      .isEqualTo(
        BlameLine(
          CommitHash("b065618bf2"),
          ZonedDateTime.of(localDateTime, zoneId),
          Email("pratul@uncommon.is"),
          1,
          "package org.simple.clinic.patient"
        )
      )
  }

  @Test
  fun `it should parse a blame line with time zone in UTC`() {
    // given
    val rawBlameLine =
      "947a7331f7 app/src/androidTest/java/org/simple/clinic/DatabaseMigrationAndroidTest.kt                    (<vinay@obvious.in>       2019-06-13 05:30:31 +0000 2406)       require(db.version == 43) { \"Required DB version: 43; Found: ${'$'}{db.version}\" }"

    // when
    val line = BlameLine.from(rawBlameLine)

    // then
    val localDateTime = LocalDateTime.of(2019, Month.JUNE, 13, 5, 30, 31)
    val zoneId = ZoneId.of("+0000")

    assertThat(line)
      .isEqualTo(
        BlameLine(
          CommitHash("947a7331f7"),
          ZonedDateTime.of(localDateTime, zoneId),
          Email("vinay@obvious.in"),
          2406,
          "      require(db.version == 43) { \"Required DB version: 43; Found: ${'$'}{db.version}\" }"
        )
      )
  }
}
