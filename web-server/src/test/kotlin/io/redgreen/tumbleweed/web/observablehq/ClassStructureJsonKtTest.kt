package io.redgreen.tumbleweed.web.observablehq

import io.redgreen.tumbleweed.ClassScanner
import io.redgreen.tumbleweed.samples.ClassWithMethodReadingField
import java.io.File
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.Test

internal class ClassStructureJsonKtTest {
  @Test
  internal fun `serialize class structure as edge bundling graph JSON`() {
    // given
    val classFile = File(
      "../bytecode-samples/build/classes/java/main/" +
        ClassWithMethodReadingField::class.java.name.replace(".", "/") + ".class"
    )
    val classStructure = ClassScanner.scan(classFile)

    // when & then
    JsonApprovals.verifyJson(classStructure.json)
  }
}
