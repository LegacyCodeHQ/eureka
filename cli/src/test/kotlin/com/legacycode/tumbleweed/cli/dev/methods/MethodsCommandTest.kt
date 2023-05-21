package com.legacycode.tumbleweed.cli.dev.methods

import com.legacycode.tumbleweed.ClassScanner
import com.legacycode.tumbleweed.testing.TestClassResource
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class MethodsCommandTest {
  @Test
  fun `create a method list`() {
    // given
    val testClassResource = TestClassResource("LoaderManager${'$'}LoaderCallbacks")
    val classStructure = ClassScanner.scan(testClassResource.file)

    // when
    val methodList = classStructure.createMethodList(emptyList())

    // then
    Approvals.verify(methodList)
  }
}
