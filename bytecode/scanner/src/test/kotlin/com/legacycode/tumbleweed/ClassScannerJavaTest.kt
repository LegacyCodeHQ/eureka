package com.legacycode.tumbleweed

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerJavaTest {
  private val defaultJavaClassLocation = ClassFileLocation(
    compiledClassesDirectory = "../samples/build/classes/java/main",
    fqClassName = "replace-java-class-?"
  )

  @Test
  fun `01 - it can scan a class with methods reading a field`() {
    // given
    val methodReadingField = defaultJavaClassLocation.copy(
      fqClassName = com.legacycode.tumbleweed.samples.MethodReadingField::class.java.name,
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
      fqClassName = com.legacycode.tumbleweed.samples.MethodWritingField::class.java.name,
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
      fqClassName = com.legacycode.tumbleweed.samples.LambdaAccessingField::class.java.name,
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
      fqClassName = com.legacycode.tumbleweed.samples.StringConcatenation::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(stringConcatenation.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `05 - it can scan a class with constants`() {
    // given
    val constants = defaultJavaClassLocation.copy(
      fqClassName = com.legacycode.tumbleweed.samples.Constants::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(constants.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `06 - it can scan a class with integer constants`() {
    // given
    val integerConstants = defaultJavaClassLocation.copy(
      fqClassName = com.legacycode.tumbleweed.samples.IntegerConstants::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(integerConstants.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `07 - it can scan a class with a constant referenced in a conditional`() {
    // given
    val constantReferencedInConditional = defaultJavaClassLocation.copy(
      fqClassName = com.legacycode.tumbleweed.samples.ConstantReferencedInConditional::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(constantReferencedInConditional.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `08 - it can scan a class with a constant referenced in return statements`() {
    // given
    val constantReferencedInReturnStatement = defaultJavaClassLocation.copy(
      fqClassName = com.legacycode.tumbleweed.samples.ConstantReferencedInReturnStatement::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(constantReferencedInReturnStatement.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `09 - it can scan a class with a static block containing a function call`() {
    // given
    val staticBlock = defaultJavaClassLocation.copy(
      fqClassName = com.legacycode.tumbleweed.samples.StaticBlock::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(staticBlock.file)

    // then
    Approvals.verify(classStructure.printable)
  }
}
