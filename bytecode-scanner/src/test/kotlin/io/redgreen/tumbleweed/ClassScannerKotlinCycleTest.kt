package io.redgreen.tumbleweed

import java.io.File
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerKotlinCycleTest {
  @Test
  fun `it can detect cycles caused by bridge functions`() {
    // given
    val tumbleweedServerClassFile = File(
      "../bytecode-samples/src/main/resources/kotlin/bridge-cycles/TumbleweedServer.class"
    )

    // when
    val classStructure = ClassScanner.scan(tumbleweedServerClassFile)

    // then
    Approvals.verify(classStructure.printable)
  }
}
