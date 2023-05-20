package com.legacycode.tumbleweed.viz.edgebundling

import com.legacycode.tumbleweed.ClassScanner
import com.legacycode.tumbleweed.samples.AccessSuperClassMembers
import com.legacycode.tumbleweed.samples.InterfaceImplementation
import com.legacycode.tumbleweed.samples.OnlyMethods
import com.legacycode.tumbleweed.testing.SampleClass
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.Test

internal class ClassStructureJsonKtTest {
  @Test
  internal fun `serialize class structure with a super type as edge bundling graph JSON`() {
    // given
    val classFile = SampleClass.Kotlin(AccessSuperClassMembers::class).file
    val classStructure = ClassScanner.scan(classFile)

    // when & then
    JsonApprovals.verifyAsJson(classStructure.toGraph())
  }

  @Test
  internal fun `serialize class structure that does not inherit a super type`() {
    // given
    val classFile = SampleClass.Kotlin(OnlyMethods::class).file
    val classStructure = ClassScanner.scan(classFile)

    // when & then
    JsonApprovals.verifyAsJson(classStructure.toGraph())
  }

  @Test
  internal fun `serialize class structure that implements an interface`() {
    // given
    val classFile = SampleClass.Kotlin(InterfaceImplementation::class).file
    val classStructure = ClassScanner.scan(classFile)

    // when & then
    JsonApprovals.verifyAsJson(classStructure.toGraph())
  }
}
