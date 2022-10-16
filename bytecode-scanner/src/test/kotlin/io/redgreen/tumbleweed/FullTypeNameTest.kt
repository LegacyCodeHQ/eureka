package io.redgreen.tumbleweed

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FullTypeNameTest {
  @ParameterizedTest
  @ValueSource(
    strings = [
      "int",
      "long",
      "short",
      "byte",
      "boolean",
      "char",
      "float",
      "double",
      "void",
    ],
  )
  fun `return the same type for primitive types`(type: String) {
    assertThat(type.simpleName)
      .isEqualTo(type)
  }

  @Test
  fun `return simple names for non-primitive types`() {
    assertThat("java.lang.String".simpleName)
      .isEqualTo("String")
  }

  @Test
  fun `return simple names for primitive arrays`() {
    assertThat("[]int".simpleName)
      .isEqualTo("[]int")
  }
}
