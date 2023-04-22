package com.legacycode.tumbleweed

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class TypeTokenTest {
  @Test
  fun `object array`() {
    // given
    val objectArrayTypeToken = TypeToken("[Ljava.util.Date")

    // when & then
    assertThat(objectArrayTypeToken.type)
      .isEqualTo("[]java.util.Date")
  }
}
