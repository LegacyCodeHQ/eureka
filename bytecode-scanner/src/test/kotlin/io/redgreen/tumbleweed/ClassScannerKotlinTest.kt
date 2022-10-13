package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.samples.AnonymousFunctionWritingField
import io.redgreen.tumbleweed.samples.EmptyClass
import io.redgreen.tumbleweed.samples.MethodReadingField
import io.redgreen.tumbleweed.samples.MethodWritingField
import io.redgreen.tumbleweed.samples.MethodsCallingMethods
import io.redgreen.tumbleweed.samples.NestedAnonymousFunctionWritingField
import io.redgreen.tumbleweed.samples.OnlyFields
import io.redgreen.tumbleweed.samples.OnlyMethods
import io.redgreen.tumbleweed.samples.RecursiveFunction
import io.redgreen.tumbleweed.samples.StaticFieldAccess
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerKotlinTest {
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
    val onlyFields = defaultKotlinClassLocation.copy(
      fqClassName = OnlyFields::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(onlyFields.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with methods`() {
    // given
    val onlyMethods = defaultKotlinClassLocation.copy(
      fqClassName = OnlyMethods::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(onlyMethods.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with methods reading a field`() {
    // given
    val methodReadingField = defaultJavaClassLocation.copy(
      fqClassName = MethodReadingField::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(methodReadingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with methods writing a field`() {
    // given
    val methodWritingField = defaultJavaClassLocation.copy(
      fqClassName = MethodWritingField::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(methodWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with methods calling methods`() {
    // given
    val methodsCallingMethods = defaultKotlinClassLocation.copy(
      fqClassName = MethodsCallingMethods::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(methodsCallingMethods.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with recursive function`() {
    // given
    val recursiveFunction = defaultKotlinClassLocation.copy(
      fqClassName = RecursiveFunction::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(recursiveFunction.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with anonymous function writing field`() {
    // given
    val anonymousFunctionWritingField = defaultKotlinClassLocation.copy(
      fqClassName = AnonymousFunctionWritingField::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(anonymousFunctionWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with nested anonymous function writing field`() {
    // given
    val nestedAnonymousFunctionWritingField = defaultKotlinClassLocation.copy(
      fqClassName = NestedAnonymousFunctionWritingField::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(nestedAnonymousFunctionWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with external class static field access and ignore them`() {
    // given
    val staticFieldAccess = defaultKotlinClassLocation.copy(
      fqClassName = StaticFieldAccess::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(staticFieldAccess.file)

    // then
    Approvals.verify(classStructure.printable)
  }
}
