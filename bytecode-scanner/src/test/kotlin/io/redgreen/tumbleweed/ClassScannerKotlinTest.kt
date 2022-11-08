package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.samples.AccessSuperClassMembers
import io.redgreen.tumbleweed.samples.AnonymousFunctionDifferentPackage
import io.redgreen.tumbleweed.samples.AnonymousFunctionWritingField
import io.redgreen.tumbleweed.samples.Counter
import io.redgreen.tumbleweed.samples.DeeplyNestedLambdaFunctions
import io.redgreen.tumbleweed.samples.EmptyClass
import io.redgreen.tumbleweed.samples.ExtendsRelationship
import io.redgreen.tumbleweed.samples.ExtensionFunctionsWithReceivers
import io.redgreen.tumbleweed.samples.ExtensionInlineFunctions
import io.redgreen.tumbleweed.samples.ExternalClassAccessingClassMembers
import io.redgreen.tumbleweed.samples.ExternalClassAccessingSuperClassMembers
import io.redgreen.tumbleweed.samples.FunctionCallsInsideLambdas
import io.redgreen.tumbleweed.samples.InlineFunction
import io.redgreen.tumbleweed.samples.InterfaceImplementation
import io.redgreen.tumbleweed.samples.LateinitVar
import io.redgreen.tumbleweed.samples.LazyProperty
import io.redgreen.tumbleweed.samples.MethodsCallingMethods
import io.redgreen.tumbleweed.samples.NestedAnonymousFunctionWritingField
import io.redgreen.tumbleweed.samples.OnlyFields
import io.redgreen.tumbleweed.samples.OnlyMethods
import io.redgreen.tumbleweed.samples.RecursiveFunction
import io.redgreen.tumbleweed.samples.StaticFieldAccess
import io.redgreen.tumbleweed.samples.SyntheticBridges
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerKotlinTest {
  private val defaultKotlinClassLocation = ClassFileLocation(
    compiledClassesDirectory = "../bytecode-samples/build/classes/kotlin/main",
    fqClassName = "replace-kotlin-class-?"
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

  @Test
  fun `it can scan a class with a lateinit field`() {
    // given
    val staticMethodAccess = defaultKotlinClassLocation.copy(
      fqClassName = LateinitVar::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(staticMethodAccess.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with a lazy field`() {
    // given
    val staticMethodAccess = defaultKotlinClassLocation.copy(
      fqClassName = LazyProperty::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(staticMethodAccess.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class that implements an interface`() {
    // given
    val interfaceImplementation = defaultKotlinClassLocation.copy(
      fqClassName = InterfaceImplementation::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(interfaceImplementation.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class that calls functions declared in the super class`() {
    // given
    val accessSuperClassMembers = defaultKotlinClassLocation.copy(
      fqClassName = AccessSuperClassMembers::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(accessSuperClassMembers.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with synthetic access functions`() {
    // given
    val syntheticBridges = defaultKotlinClassLocation.copy(
      fqClassName = SyntheticBridges::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(syntheticBridges.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with extension functions with receivers`() {
    // given
    val extensionFunctionsWithReceivers = defaultKotlinClassLocation.copy(
      fqClassName = ExtensionFunctionsWithReceivers::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(extensionFunctionsWithReceivers.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with calls to a function inside lambdas`() {
    // given
    val functionCallsInsideLambdas = defaultKotlinClassLocation.copy(
      fqClassName = FunctionCallsInsideLambdas::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(functionCallsInsideLambdas.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with deeply nested lambda calls`() {
    // given
    val deeplyNestedLambdaFunctions = defaultKotlinClassLocation.copy(
      fqClassName = DeeplyNestedLambdaFunctions::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(deeplyNestedLambdaFunctions.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with an inline function`() {
    // given
    val inlineFunction = defaultKotlinClassLocation.copy(
      fqClassName = InlineFunction::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(inlineFunction.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can scan a class with a copy constructor`() {
    // given
    val copyConstructor = defaultKotlinClassLocation.copy(
      fqClassName = Counter::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(copyConstructor.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can find relationships from anonymous functions from different package`() {
    // given
    val anonymousFunctionDifferentPackage = defaultKotlinClassLocation.copy(
      fqClassName = AnonymousFunctionDifferentPackage::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(anonymousFunctionDifferentPackage.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can find relationships referenced from inline extension functions`() {
    // given
    val extensionInlineFunctions = defaultKotlinClassLocation.copy(
      fqClassName = ExtensionInlineFunctions::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(extensionInlineFunctions.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can find the super class`() {
    // given
    val extendsRelationship = defaultKotlinClassLocation.copy(
      fqClassName = ExtendsRelationship::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(extendsRelationship.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can find the interfaces implemented by a class`() {
    // given
    val implementsInterface = defaultKotlinClassLocation.copy(
      fqClassName = InterfaceImplementation::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(implementsInterface.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can find relationships in external classes accessing class members`() {
    // given
    val externalClassesAccessingClassMembers = defaultKotlinClassLocation.copy(
      fqClassName = ExternalClassAccessingClassMembers::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(externalClassesAccessingClassMembers.file)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can find relationships in external classes accessing super class members`() {
    // given
    val externalClassesAccessingSuperClassMembers = defaultKotlinClassLocation.copy(
      fqClassName = ExternalClassAccessingSuperClassMembers::class.java.name,
    )

    // when
    val classStructure = ClassScanner.scan(externalClassesAccessingSuperClassMembers.file)

    // then
    Approvals.verify(classStructure.printable)
  }
}
