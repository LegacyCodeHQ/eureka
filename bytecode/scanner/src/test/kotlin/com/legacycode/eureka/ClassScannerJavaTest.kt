package com.legacycode.eureka

import com.legacycode.eureka.samples.ConstantReferencedInConditional
import com.legacycode.eureka.samples.ConstantReferencedInReturnStatement
import com.legacycode.eureka.samples.Constants
import com.legacycode.eureka.samples.IntegerConstants
import com.legacycode.eureka.samples.LambdaAccessingField
import com.legacycode.eureka.samples.MethodReadingField
import com.legacycode.eureka.samples.MethodWritingField
import com.legacycode.eureka.samples.StaticBlock
import com.legacycode.eureka.samples.StringConcatenation
import com.legacycode.eureka.samples.Video
import com.legacycode.eureka.testing.SampleClass
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerJavaTest {
  private val scanner = ClassScanner()

  @Test
  fun `01 - it can scan a class with methods reading a field`() {
    // given
    val methodReadingField = SampleClass.Java(MethodReadingField::class)

    // when
    val classStructure = scanner.scan(methodReadingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `02 - it can scan a class with methods writing a field`() {
    // given
    val methodWritingField = SampleClass.Java(MethodWritingField::class)

    // when
    val classStructure = scanner.scan(methodWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `03 - it can scan a class with a lambda function accessing a field`() {
    // given
    val methodReadingAndWritingField = SampleClass.Java(LambdaAccessingField::class)

    // when
    val classStructure = scanner.scan(methodReadingAndWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `04 - it can scan a class with string concatenation`() {
    // given
    val stringConcatenation = SampleClass.Java(StringConcatenation::class)

    // when
    val classStructure = scanner.scan(stringConcatenation.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `05 - it can scan a class with constants`() {
    // given
    val constants = SampleClass.Java(Constants::class)

    // when
    val classStructure = scanner.scan(constants.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `06 - it can scan a class with integer constants`() {
    // given
    val integerConstants = SampleClass.Java(IntegerConstants::class)

    // when
    val classStructure = scanner.scan(integerConstants.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `07 - it can scan a class with a constant referenced in a conditional`() {
    // given
    val constantReferencedInConditional = SampleClass.Java(ConstantReferencedInConditional::class)

    // when
    val classStructure = scanner.scan(constantReferencedInConditional.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `08 - it can scan a class with a constant referenced in return statements`() {
    // given
    val constantReferencedInReturnStatement = SampleClass.Java(ConstantReferencedInReturnStatement::class)

    // when
    val classStructure = scanner.scan(constantReferencedInReturnStatement.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `09 - it can scan a class with a static block containing a function call`() {
    // given
    val staticBlock = SampleClass.Java(StaticBlock::class)

    // when
    val classStructure = scanner.scan(staticBlock.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `09 - it can scan a Java record`() {
    // given
    val javaRecord = SampleClass.Java(Video::class)

    // when
    val classStructure = scanner.scan(javaRecord.file)

    // then
    Approvals.verify(classStructure.printable)
  }
}
