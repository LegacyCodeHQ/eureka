package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.samples.ClassWithFields
import io.redgreen.tumbleweed.samples.ClassWithMethods
import io.redgreen.tumbleweed.samples.EmptyClass
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerTest {
  private val defaultClassLocation = ClassFileLocation(
    compiledClassesDirectory = "../bytecode-samples/build/classes/kotlin/main",
    fqClassName = "replace-me-?"
  )

  @Test
  fun `it can scan an empty class`() {
    // given
    val emptyClass = defaultClassLocation.copy(
      fqClassName = EmptyClass::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(emptyClass)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with fields`() {
    // given
    val classWithFields = defaultClassLocation.copy(
      fqClassName = ClassWithFields::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(classWithFields)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with methods`() {
    // given
    val classWithMethods = defaultClassLocation.copy(
      fqClassName = ClassWithMethods::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(classWithMethods)

    // then
    Approvals.verify(classStructure.printable)
  }
}
