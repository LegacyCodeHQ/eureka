package io.redgreen.tumbleweed

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerTest {
  @Test
  fun `it can scan an empty class`() {
    // given
    val emptyClass = ClassFileLocation(
      compiledClassesDirectory = "../bytecode-samples/build/classes/kotlin/main",
      fqClassName = "io.redgreen.tumbleweed.samples.EmptyClass"
    )

    // when
    val classStructure = ClassScanner.scan(emptyClass)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with fields`() {
    // given
    val classWithFields = ClassFileLocation(
      compiledClassesDirectory = "../bytecode-samples/build/classes/kotlin/main",
      fqClassName = "io.redgreen.tumbleweed.samples.ClassWithFields"
    )

    // when
    val classStructure = ClassScanner.scan(classWithFields)

    // then
    Approvals.verify(classStructure.printable)
  }
}
