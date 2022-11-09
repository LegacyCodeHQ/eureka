package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.samples.LambdaAccessingField
import io.redgreen.tumbleweed.samples.MethodReadingField
import io.redgreen.tumbleweed.samples.MethodWritingField
import io.redgreen.tumbleweed.samples.StaticFieldJava
import io.redgreen.tumbleweed.samples.StringConcatenation
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerJavaTest {
  private val defaultJavaClassLocation = ClassFileLocation(
    compiledClassesDirectory = "../bytecode-samples/build/classes/java/main",
    fqClassName = "replace-java-class-?"
  )

  @Test
  fun `01 - it can scan a class with methods reading a field`() {
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
  fun `02 - it can scan a class with methods writing a field`() {
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
  fun `03 - it can scan a class with a lambda function accessing a field`() {
    // given
    val methodReadingAndWritingField = defaultJavaClassLocation.copy(
      fqClassName = LambdaAccessingField::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(methodReadingAndWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `04 - it can scan a class with string concatenation`() {
    // given
    val stringConcatenation = defaultJavaClassLocation.copy(
      fqClassName = StringConcatenation::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(stringConcatenation.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `04 - it can scan a class with static field`() {
    // given
    val staticFieldJava = defaultJavaClassLocation.copy(
      fqClassName = StaticFieldJava::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(staticFieldJava.file)

    // then
    Approvals.verify(classStructure.printable)
  }
}
