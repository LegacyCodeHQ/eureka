package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.samples.LambdaAccessingField
import io.redgreen.tumbleweed.samples.MethodReadingField
import io.redgreen.tumbleweed.samples.MethodWritingField
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerJavaTest {
  private val defaultJavaClassLocation = ClassFileLocation(
    compiledClassesDirectory = "../bytecode-samples/build/classes/java/main",
    fqClassName = "replace-java-class-?"
  )

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
  fun `it can scan a class with a lambda function accessing a field`() {
    // given
    val methodReadingAndWritingField = defaultJavaClassLocation.copy(
      fqClassName = LambdaAccessingField::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(methodReadingAndWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }
}
