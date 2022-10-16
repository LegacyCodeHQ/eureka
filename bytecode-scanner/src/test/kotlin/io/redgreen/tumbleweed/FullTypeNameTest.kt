package io.redgreen.tumbleweed

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FullTypeNameTest {
  @ParameterizedTest
  @ValueSource(
    strings = [
      "int",
    ],
  )
  fun `return the same type for primitive types`(type: String) {
    assertThat(type.simpleName)
      .isEqualTo(type)
  }
}
