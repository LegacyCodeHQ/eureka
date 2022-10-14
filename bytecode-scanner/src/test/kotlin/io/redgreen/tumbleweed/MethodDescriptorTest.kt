package io.redgreen.tumbleweed

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class MethodDescriptorTest {
  @Test
  fun `it can parse method descriptor with a mix of object and primitive arrays`() {
    // given
    val descriptor = MethodDescriptor("(I[Ljava/lang/String;[I)V")

    // when
    val returnType = descriptor.returnType
    val parameters = descriptor.parameters

    // then
    assertThat(returnType)
      .isEqualTo("void")

    assertThat(parameters)
      .containsExactly("int", "[]java.lang.String", "[]int")
      .inOrder()
  }
}
