package com.legacycode.ureka.gradle

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SubprojectDependencyTest {
  @ParameterizedTest
  @ValueSource(
    strings = [
      "+--- project libsignal-service",
      "+--- project :libsignal-service",
      "+--- project libsignal-service (n)",
      "+--- project libsignal-service (c)",
      "+--- project libsignal-service (*)",
      "+--- project :libsignal-service FAILED",
    ]
  )
  fun `it can parse variations of the subproject dependency patterns`(
    subprojectDependencyLine: String,
  ) {
    // when
    val dependency = SubprojectDependency.from(subprojectDependencyLine)!!

    // then
    assertThat(dependency.name)
      .isEqualTo("libsignal-service")
  }

  @Test
  fun `it does not parse variation that are not on level 1`() {
    // given
    val subprojectDependencyLine = "|    +--- project :core-util (*)"

    // when
    val dependency = SubprojectDependency.from(subprojectDependencyLine)

    // then
    assertThat(dependency)
      .isNull()
  }
}
