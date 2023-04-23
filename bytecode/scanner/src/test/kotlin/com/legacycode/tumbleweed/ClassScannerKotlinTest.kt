package com.legacycode.tumbleweed

import com.legacycode.tumbleweed.samples.AnonymousFunctionDifferentPackage
import com.legacycode.tumbleweed.samples.AnonymousFunctionWritingField
import com.legacycode.tumbleweed.samples.Counter
import com.legacycode.tumbleweed.samples.DeeplyNestedLambdaFunctions
import com.legacycode.tumbleweed.samples.EmptyClass
import com.legacycode.tumbleweed.samples.ExtendsRelationship
import com.legacycode.tumbleweed.samples.ExtensionFunctionsWithReceivers
import com.legacycode.tumbleweed.samples.ExtensionInlineFunctions
import com.legacycode.tumbleweed.samples.ExternalClassAccessingClassMembers
import com.legacycode.tumbleweed.samples.ExternalClassAccessingSuperClassMembers
import com.legacycode.tumbleweed.samples.FunctionCallsInsideLambdas
import com.legacycode.tumbleweed.samples.InlineFunction
import com.legacycode.tumbleweed.samples.InterfaceImplementation
import com.legacycode.tumbleweed.samples.LateinitVar
import com.legacycode.tumbleweed.samples.LazyProperty
import com.legacycode.tumbleweed.samples.MethodsCallingMethods
import com.legacycode.tumbleweed.samples.NestedAnonymousFunctionWritingField
import com.legacycode.tumbleweed.samples.OnlyFields
import com.legacycode.tumbleweed.samples.OnlyMethods
import com.legacycode.tumbleweed.samples.RecursiveFunction
import com.legacycode.tumbleweed.samples.StaticFieldAccess
import com.legacycode.tumbleweed.samples.SyntheticBridges
import com.legacycode.tumbleweed.testing.SampleClass
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerKotlinTest {
  @Test
  fun `01 - it can scan an empty class`() {
    // given
    val emptyClass = SampleClass.Kotlin(EmptyClass::class)

    // when
    val classStructure = ClassScanner.scan(emptyClass.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `02 - it can scan a class with fields`() {
    // given
    val onlyFields = SampleClass.Kotlin(OnlyFields::class)

    // when
    val classStructure = ClassScanner.scan(onlyFields.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `03 - it can scan a class with methods`() {
    // given
    val onlyMethods = SampleClass.Kotlin(OnlyMethods::class)

    // when
    val classStructure = ClassScanner.scan(onlyMethods.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `04 - it can scan a class with methods calling methods`() {
    // given
    val methodsCallingMethods = SampleClass.Kotlin(MethodsCallingMethods::class)

    // when
    val classStructure = ClassScanner.scan(methodsCallingMethods.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `05 - it can scan a class with recursive function`() {
    // given
    val recursiveFunction = SampleClass.Kotlin(RecursiveFunction::class)

    // when
    val classStructure = ClassScanner.scan(recursiveFunction.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `06 - it can scan a class with anonymous function writing field`() {
    // given
    val anonymousFunctionWritingField = SampleClass.Kotlin(AnonymousFunctionWritingField::class)

    // when
    val classStructure = ClassScanner.scan(anonymousFunctionWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `07 - it can scan a class with nested anonymous function writing field`() {
    // given
    val nestedAnonymousFunctionWritingField = SampleClass.Kotlin(NestedAnonymousFunctionWritingField::class)

    // when
    val classStructure = ClassScanner.scan(nestedAnonymousFunctionWritingField.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `08 - it can scan a class with external class static field access and ignore them`() {
    // given
    val staticFieldAccess = SampleClass.Kotlin(StaticFieldAccess::class)

    // when
    val classStructure = ClassScanner.scan(staticFieldAccess.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `09 - it can scan a class with a lateinit field`() {
    // given
    val staticMethodAccess = SampleClass.Kotlin(LateinitVar::class)

    // when
    val classStructure = ClassScanner.scan(staticMethodAccess.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `10 - it can scan a class with a lazy field`() {
    // given
    val staticMethodAccess = SampleClass.Kotlin(LazyProperty::class)

    // when
    val classStructure = ClassScanner.scan(staticMethodAccess.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `11 - it can scan a class that implements an interface`() {
    // given
    val interfaceImplementation = SampleClass.Kotlin(InterfaceImplementation::class)

    // when
    val classStructure = ClassScanner.scan(interfaceImplementation.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `12 - it can scan a class that calls functions declared in the super class`() {
    // given
    val accessSuperClassMembers =
      SampleClass.Kotlin(com.legacycode.tumbleweed.samples.AccessSuperClassMembers::class)

    // when
    val classStructure = ClassScanner.scan(accessSuperClassMembers.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `13 - it can scan a class with synthetic access functions`() {
    // given
    val syntheticBridges = SampleClass.Kotlin(SyntheticBridges::class)

    // when
    val classStructure = ClassScanner.scan(syntheticBridges.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `14 - it can scan a class with extension functions with receivers`() {
    // given
    val extensionFunctionsWithReceivers = SampleClass.Kotlin(ExtensionFunctionsWithReceivers::class)

    // when
    val classStructure = ClassScanner.scan(extensionFunctionsWithReceivers.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `15 - it can scan a class with calls to a function inside lambdas`() {
    // given
    val functionCallsInsideLambdas = SampleClass.Kotlin(FunctionCallsInsideLambdas::class)

    // when
    val classStructure = ClassScanner.scan(functionCallsInsideLambdas.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `16 - it can scan a class with deeply nested lambda calls`() {
    // given
    val deeplyNestedLambdaFunctions = SampleClass.Kotlin(DeeplyNestedLambdaFunctions::class)

    // when
    val classStructure = ClassScanner.scan(deeplyNestedLambdaFunctions.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `17 - it can scan a class with an inline function`() {
    // given
    val inlineFunction = SampleClass.Kotlin(InlineFunction::class)

    // when
    val classStructure = ClassScanner.scan(inlineFunction.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `18 - it can scan a class with a copy constructor`() {
    // given
    val copyConstructor = SampleClass.Kotlin(Counter::class)

    // when
    val classStructure = ClassScanner.scan(copyConstructor.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `19 - it can find relationships from anonymous functions from different package`() {
    // given
    val anonymousFunctionDifferentPackage = SampleClass.Kotlin(AnonymousFunctionDifferentPackage::class)

    // when
    val classStructure = ClassScanner.scan(anonymousFunctionDifferentPackage.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `20 - it can find relationships referenced from inline extension functions`() {
    // given
    val extensionInlineFunctions = SampleClass.Kotlin(ExtensionInlineFunctions::class)

    // when
    val classStructure = ClassScanner.scan(extensionInlineFunctions.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `21 - it can find the super class`() {
    // given
    val extendsRelationship = SampleClass.Kotlin(ExtendsRelationship::class)

    // when
    val classStructure = ClassScanner.scan(extendsRelationship.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `22 - it can find the interfaces implemented by a class`() {
    // given
    val implementsInterface = SampleClass.Kotlin(InterfaceImplementation::class)

    // when
    val classStructure = ClassScanner.scan(implementsInterface.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `23 - it can find relationships in external classes accessing class members`() {
    // given
    val externalClassesAccessingClassMembers = SampleClass.Kotlin(ExternalClassAccessingClassMembers::class)

    // when
    val classStructure = ClassScanner.scan(externalClassesAccessingClassMembers.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `24 - it can find relationships in external classes accessing super class members`() {
    // given
    val externalClassesAccessingSuperClassMembers =
      SampleClass.Kotlin(ExternalClassAccessingSuperClassMembers::class)

    // when
    val classStructure = ClassScanner.scan(externalClassesAccessingSuperClassMembers.file)

    // then
    Approvals.verify(classStructure.printable)
  }
}
