package com.legacycode.tumbleweed.web

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class MethodListTest {
  @Test
  fun `create a method list from a resource file`() {
    // given
    val methodList = MethodList.fromResource("android.app.Activity")

    // when
    val methodSignatures = methodList.methodSignatures.joinToString("\n")

    // then
    Approvals.verify(methodSignatures)
  }
}
