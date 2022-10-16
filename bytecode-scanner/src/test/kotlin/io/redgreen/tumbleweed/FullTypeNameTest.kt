package io.redgreen.tumbleweed

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class FullTypeNameTest {
  @Test
  fun `return the same type for primitive types`() {
    assertThat("int".simpleName)
      .isEqualTo("int")
  }
}
