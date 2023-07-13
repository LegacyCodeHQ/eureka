package com.legacycode.eureka.dex

import com.legacycode.eureka.dex.test.TestArtifact
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class JvmArtifactParserTest {
  @Test
  fun `it can build an inheritance adjacency list from a JAR`() {
    // given
    val jarFile = TestArtifact("truth-1.1.5.jar").file
    val parser = JvmArtifactParser(jarFile)
    val adjacencyList = parser.inheritanceAdjacencyList()

    // when
    val graphvizTree = adjacencyList.tree(
      Ancestor("Lcom/google/common/truth/Subject;"),
      DotTreeBuilder("Truth Subjects"),
    )

    // then
    Approvals.verify(graphvizTree)
  }
}
