package com.legacycode.eureka.cli.dev.methods

import com.legacycode.eureka.ClassScanner
import com.legacycode.eureka.testing.TestClassResource
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class MethodsCommandTest {
  @Test
  fun `create a method list`() {
    // given
    val testClassResource = TestClassResource("LoaderManager${'$'}LoaderCallbacks")
    val classStructure = ClassScanner().scan(testClassResource.file)

    // when
    val methodList = classStructure.createMethodList(emptyList())

    // then
    Approvals.verify(methodList)
  }
}
