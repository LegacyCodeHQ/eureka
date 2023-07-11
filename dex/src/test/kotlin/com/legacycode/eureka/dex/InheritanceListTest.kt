package com.legacycode.eureka.dex

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class InheritanceListTest {
  @Test
  fun `it can create an empty inheritance list`() {
    // given
    val inheritanceList = InheritanceList()

    // when & then
    assertThat(inheritanceList.isEmpty)
      .isTrue()
  }
}
