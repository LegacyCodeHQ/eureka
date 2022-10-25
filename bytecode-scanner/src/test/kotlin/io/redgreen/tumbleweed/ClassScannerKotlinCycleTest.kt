package io.redgreen.tumbleweed

import java.io.File
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerKotlinCycleTest {
  @Test
  fun `it can detect cycles caused by bridge functions`() {
    // given
    val tumbleweedServerClassFile = File(
      "../bytecode-samples/src/main/resources/precompiled/kotlin/bridge-cycles/TumbleweedServer.class"
    )

    // when
    val classStructure = ClassScanner.scan(tumbleweedServerClassFile)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `it can detect cycles caused by cyclic function calls`() {
    // given
    val classStructureClass = File(
      "../bytecode-samples/src/main/resources/precompiled/kotlin/cyclic-function-calls/ClassStructure.class"
    )

    // when
    val classStructure = ClassScanner.scan(classStructureClass)

    // then
    Approvals.verify(classStructure.printable)
  }
}
