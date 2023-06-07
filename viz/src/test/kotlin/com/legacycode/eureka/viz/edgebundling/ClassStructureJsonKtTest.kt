package com.legacycode.eureka.viz.edgebundling

import com.legacycode.eureka.ClassScanner
import com.legacycode.eureka.samples.AccessSuperClassMembers
import com.legacycode.eureka.samples.InterfaceImplementation
import com.legacycode.eureka.samples.OnlyMethods
import com.legacycode.eureka.testing.SampleClass
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.Test

internal class ClassStructureJsonKtTest {
  private val scanner = ClassScanner()

  @Test
  internal fun `serialize class structure with a super type as edge bundling graph JSON`() {
    // given
    val classFile = SampleClass.Kotlin(AccessSuperClassMembers::class).file
    val classStructure = scanner.scan(classFile)

    // when & then
    JsonApprovals.verifyAsJson(classStructure.toGraph())
  }

  @Test
  internal fun `serialize class structure that does not inherit a super type`() {
    // given
    val classFile = SampleClass.Kotlin(OnlyMethods::class).file
    val classStructure = scanner.scan(classFile)

    // when & then
    JsonApprovals.verifyAsJson(classStructure.toGraph())
  }

  @Test
  internal fun `serialize class structure that implements an interface`() {
    // given
    val classFile = SampleClass.Kotlin(InterfaceImplementation::class).file
    val classStructure = scanner.scan(classFile)

    // when & then
    JsonApprovals.verifyAsJson(classStructure.toGraph())
  }
}
