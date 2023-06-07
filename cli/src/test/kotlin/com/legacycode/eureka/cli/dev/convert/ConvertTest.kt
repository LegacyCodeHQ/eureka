package com.legacycode.eureka.cli.dev.convert

import com.legacycode.eureka.viz.edgebundling.EdgeBundlingGraph
import org.approvaltests.JsonApprovals
import org.junit.jupiter.api.Test

class ConvertTest {
  @Test
  fun `it can convert a csv to an edge bundling graph`() {
    // given
    val csv = """
      m.signature,f.signature
      void getName(),String name
      void setName(String),String name
    """.trimIndent()

    // when
    val graph = EdgeBundlingGraph.from(csv)

    // then
    JsonApprovals.verifyAsJson(graph)
  }
}
