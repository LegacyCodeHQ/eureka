package com.legacycode.tumbleweed.web.observablehq

import com.legacycode.tumbleweed.ClassScanner
import com.legacycode.tumbleweed.samples.InterfaceImplementation
import com.legacycode.tumbleweed.samples.OnlyMethods
import java.io.File
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.Test

internal class ClassStructureJsonKtTest {
  @Test
  internal fun `serialize class structure with a super type as edge bundling graph JSON`() {
    // given
    val classFile = File(
      "../bytecode/samples/build/classes/kotlin/main/" +
        com.legacycode.tumbleweed.samples.AccessSuperClassMembers::class.java.name.replace(".", "/") + ".class"
    )
    val classStructure = ClassScanner.scan(classFile)

    // when & then
    JsonApprovals.verifyAsJson(classStructure.graph)
  }

  @Test
  internal fun `serialize class structure that does not inherit a super type`() {
    // given
    val classFile = File(
      "../bytecode/samples/build/classes/kotlin/main/" +
        OnlyMethods::class.java.name.replace(".", "/") + ".class"
    )
    val classStructure = ClassScanner.scan(classFile)

    // when & then
    JsonApprovals.verifyAsJson(classStructure.graph)
  }

  @Test
  internal fun `serialize class structure that implements an interface`() {
    // given
    val classFile = File(
      "../bytecode/samples/build/classes/kotlin/main/" +
        InterfaceImplementation::class.java.name.replace(".", "/") + ".class"
    )
    val classStructure = ClassScanner.scan(classFile)

    // when & then
    JsonApprovals.verifyAsJson(classStructure.graph)
  }
}