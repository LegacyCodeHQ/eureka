package com.legacycode.eureka.gradle.metrics

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class InstabilityTest {
  @Test
  fun `maximally stable`() {
    val instability = Instability(0, 19)

    assertThat(instability.value)
      .isEqualTo("0")
  }

  @Test
  fun `maximally unstable`() {
    val instability = Instability(1, 0)

    assertThat(instability.value)
      .isEqualTo("1")
  }

  @Test
  fun `somewhere in-between`() {
    val instability = Instability(2, 1)

    assertThat(instability.value)
      .isEqualTo("0.67")
  }

  @Test
  fun unused() {
    val instability = Instability(0, 0)

    assertThat(instability.value)
      .isEqualTo("∞")
  }
}
