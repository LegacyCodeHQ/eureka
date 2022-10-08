package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.samples.ClassWithAnonymousFunctionWritingField
import io.redgreen.tumbleweed.samples.ClassWithFields
import io.redgreen.tumbleweed.samples.ClassWithMethodReadingField
import io.redgreen.tumbleweed.samples.ClassWithMethodWritingField
import io.redgreen.tumbleweed.samples.ClassWithMethods
import io.redgreen.tumbleweed.samples.ClassWithMethodsCallingMethods
import io.redgreen.tumbleweed.samples.ClassWithRecursiveFunction
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
    val classStructure = ClassScanner.scan(emptyClass.file)

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
    val classStructure = ClassScanner.scan(classWithFields.file)

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
    val classStructure = ClassScanner.scan(classWithMethods.file)

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
    val classStructure = ClassScanner.scan(methodReadingFieldClass.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with methods writing a field`() {
    // given
    val methodWritingFieldClass = defaultJavaClassLocation.copy(
      fqClassName = ClassWithMethodWritingField::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(methodWritingFieldClass.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with methods calling methods`() {
    // given
    val classWithMethodsCallingMethods = defaultKotlinClassLocation.copy(
      fqClassName = ClassWithMethodsCallingMethods::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(classWithMethodsCallingMethods.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with recursive function`() {
    // given
    val classWithRecursiveFunction = defaultKotlinClassLocation.copy(
      fqClassName = ClassWithRecursiveFunction::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(classWithRecursiveFunction.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with anonymous function writing field`() {
    // given
    val classWithAnonymousFunctionWritingField = defaultKotlinClassLocation.copy(
      fqClassName = ClassWithAnonymousFunctionWritingField::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(classWithAnonymousFunctionWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }
}
