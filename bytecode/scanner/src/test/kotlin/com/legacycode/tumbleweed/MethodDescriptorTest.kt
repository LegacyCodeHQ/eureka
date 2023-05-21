package com.legacycode.tumbleweed

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

  @Test
  fun `it can parse method descriptor with reference types and primitive array`() {
    // given
    val descriptor = MethodDescriptor("(Lmessages/SignalServiceContent;[BLrecipients/Recipient;)V")

    // when
    val returnType = descriptor.returnType
    val parameters = descriptor.parameters

    // then
    assertThat(returnType)
      .isEqualTo("void")

    assertThat(parameters)
      .containsExactly("messages.SignalServiceContent", "[]byte", "recipients.Recipient")
      .inOrder()
  }

  @Test
  fun `it can parse method descriptor with reference types`() {
    // given
    val descriptor = MethodDescriptor("(Landroidx/loader/content/Loader;Ljava/lang/Object;)V")

    // when
    val returnType = descriptor.returnType
    val parameters = descriptor.parameters

    // then
    assertThat(returnType)
      .isEqualTo("void")

    assertThat(parameters)
      .containsExactly("androidx.loader.content.Loader", "java.lang.Object")
      .inOrder()
  }

  @Test
  fun `it can parse a method descriptor with a single reference array type`() {
    // given
    val descriptor = MethodDescriptor("([Ljava/lang/String;)V")

    // when
    val returnType = descriptor.returnType
    val parameters = descriptor.parameters

    // then
    assertThat(returnType)
      .isEqualTo("void")

    assertThat(parameters)
      .containsExactly("[]java.lang.String")
      .inOrder()
  }

  @Test
  fun `it can parse a method descriptor with a single reference type`() {
    // given
    val descriptor = MethodDescriptor("(Ljava/lang/String;)V")

    // when
    val returnType = descriptor.returnType
    val parameters = descriptor.parameters

    // then
    assertThat(returnType)
      .isEqualTo("void")

    assertThat(parameters)
      .containsExactly("java.lang.String")
      .inOrder()
  }
}
