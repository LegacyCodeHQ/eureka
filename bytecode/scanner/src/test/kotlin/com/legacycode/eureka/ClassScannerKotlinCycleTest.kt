package com.legacycode.eureka

import java.io.File
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ClassScannerKotlinCycleTest {
  private val scanner = ClassScanner()

  @Test
  fun `01 - it can detect cycles caused by bridge functions`() {
    // given
    val serverClassFile = File(
      "../samples/src/main/resources/precompiled/kotlin/bridge-cycles/TumbleweedServer.class"
    )

    // when
    val classStructure = scanner.scan(serverClassFile)

    // then
    Approvals.verify(classStructure.printable)
  }

  @Test
  fun `02 - it can detect cycles caused by cyclic function calls`() {
    // given
    val classStructureClass = File(
      "../samples/src/main/resources/precompiled/kotlin/cyclic-function-calls/ClassStructure.class"
    )

    // when
    val classStructure = scanner.scan(classStructureClass)

    // then
    Approvals.verify(classStructure.printable)
  }
}
