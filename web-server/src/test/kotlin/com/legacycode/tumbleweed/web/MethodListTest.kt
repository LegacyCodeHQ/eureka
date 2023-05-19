package com.legacycode.tumbleweed.web

import com.google.common.truth.Truth.assertThat
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class MethodListTest {
  private val methodList = MethodList.fromResource("android.app.Activity")

  @Test
  fun `create a method list from a resource file`() {
    // when
    val methodSignatures = methodList.methodSignatures.joinToString("\n")

    // then
    Approvals.verify(methodSignatures)
  }

  @Test
  fun `returns false for a method signature not on the list`() {
    assertThat(methodList.has("boolean setTransparent(boolean)"))
      .isFalse()
  }

  @Test
  fun `returns true for a method signature on the list`() {
    assertThat(methodList.has("boolean setTranslucent(boolean)"))
      .isTrue()
  }
}
