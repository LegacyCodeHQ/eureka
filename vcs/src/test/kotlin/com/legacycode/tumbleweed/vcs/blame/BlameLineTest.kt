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

  @Test
  fun `it should parse a blame line with no content`() {
    // given
    val rawBlameLine = "597f7bbafd2 proguard-jackson.pro (<me@jake.su> 2015-04-01 18:04:23 -0700 12)"

    // when
    val line = BlameLine.from(rawBlameLine)

    // then
    val localDateTime = LocalDateTime.of(2015, Month.APRIL, 1, 18, 4, 23)
    val zoneId = ZoneId.of("-0700")

    assertThat(line)
      .isEqualTo(
        BlameLine(
          CommitHash("597f7bbafd2"),
          ZonedDateTime.of(localDateTime, zoneId),
          Email("me@jake.su"),
          12,
          ""
        )
      )
  }

  @Test
  fun `it should parse a blame line with commit hashes prefixed with a caret`() {
    // given
    val rawBlameLine = "^bbea3fe1b1 (<moxie@thoughtcrime.org>    2011-12-20 10:20:44 -0800  1) .classpath"

    // when
    val line = BlameLine.from(rawBlameLine)

    // then
    val localDateTime = LocalDateTime.of(2011, Month.DECEMBER, 20, 10, 20, 44)
    val zoneId = ZoneId.of("-0800")

    assertThat(line)
      .isEqualTo(
        BlameLine(
          CommitHash("bbea3fe1b1"),
          ZonedDateTime.of(localDateTime, zoneId),
          Email("moxie@thoughtcrime.org"),
          1,
          ".classpath"
        )
      )
  }

  @Test
  fun `it can parse blame line with content containing angled brackets`() {
    // given
    val rawBlameLine = "174cd860a04 app/src/androidTest/java/org/thoughtcrime/securesms/database/DistributionListDatabaseTest.kt (<alex@signal.org>     2022-02-24 13:40:28 -0400 40)     val members: List<RecipientId> = recipientList(1, 2, 3)"

    // when
    val line = BlameLine.from(rawBlameLine)

    // then
    val localDateTime = LocalDateTime.of(2022, Month.FEBRUARY, 24, 13, 40, 28)
    val zoneId = ZoneId.of("-0400")

    assertThat(line)
      .isEqualTo(
        BlameLine(
          CommitHash("174cd860a04"),
          ZonedDateTime.of(localDateTime, zoneId),
          Email("alex@signal.org"),
          40,
          "    val members: List<RecipientId> = recipientList(1, 2, 3)"
        )
      )
  }

  @Test
  fun `it can parse blame line with whitespace unicode chars`() {
    // given
    val rawBlameLine = "fddba2906ab app/src/main/res/values/strings.xml (<android@signal.org>                                2021-04-06 13:03:33 -0300 3082)     <string name=\"PaymentsHomeFragment__learn_more__activate_payments\" translatable=\"false\">https://support.signal.org/hc/articles/360057625692#payments_activate\u2028</string>"

    // when
    val line = BlameLine.from(rawBlameLine)

    // then
    val localDateTime = LocalDateTime.of(2021, Month.APRIL, 6, 13, 3, 33)
    val zoneId = ZoneId.of("-0300")

    assertThat(line)
      .isEqualTo(
        BlameLine(
          CommitHash("fddba2906ab"),
          ZonedDateTime.of(localDateTime, zoneId),
          Email("android@signal.org"),
          3082,
          "    <string name=\"PaymentsHomeFragment__learn_more__activate_payments\" translatable=\"false\">https://support.signal.org/hc/articles/360057625692#payments_activate\u2028</string>"
        )
      )
  }
}
