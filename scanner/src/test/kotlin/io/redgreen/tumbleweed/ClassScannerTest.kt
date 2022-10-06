package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.samples.ClassWithFields
import io.redgreen.tumbleweed.samples.ClassWithMethodReadingField
import io.redgreen.tumbleweed.samples.ClassWithMethods
import io.redgreen.tumbleweed.samples.EmptyClass
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerTest {
  private val defaultKotlinClassLocation = ClassFileLocation(
    compiledClassesDirectory = "../bytecode-samples/build/classes/kotlin/main",
    fqClassName = "replace-kotlin-class-?"
  )

  private val defaultJavaClassLocation = ClassFileLocation(
    compiledClassesDirectory = "../bytecode-samples/build/classes/java/main",
    fqClassName = "replace-java-class-?"
  )

  @Test
  fun `it can scan an empty class`() {
    // given
    val emptyClass = defaultKotlinClassLocation.copy(
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
    val classWithFields = defaultKotlinClassLocation.copy(
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
    val classWithMethods = defaultKotlinClassLocation.copy(
      fqClassName = ClassWithMethods::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(classWithMethods)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with methods reading a field`() {
    // given
    val methodReadingFieldClass = defaultJavaClassLocation.copy(
      fqClassName = ClassWithMethodReadingField::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(methodReadingFieldClass)

    // then
    Approvals.verify(classStructure.printable)
  }
}
