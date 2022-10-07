package io.redgreen.tumbleweed.web.observablehq

import io.redgreen.tumbleweed.ClassFileLocation
import io.redgreen.tumbleweed.ClassScanner
import io.redgreen.tumbleweed.samples.ClassWithMethodReadingField
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.Test

internal class ClassStructureJsonKtTest {
  @Test
  internal fun `serialize class structure as edge bundling graph JSON`() {
    // given
    val methodReadingFieldClass = ClassFileLocation(
      compiledClassesDirectory = "../bytecode-samples/build/classes/java/main",
      fqClassName = ClassWithMethodReadingField::class.java.name,
    )
    val classStructure = ClassScanner.scan(methodReadingFieldClass)

    // when & then
    JsonApprovals.verifyJson(classStructure.json)
  }
}
