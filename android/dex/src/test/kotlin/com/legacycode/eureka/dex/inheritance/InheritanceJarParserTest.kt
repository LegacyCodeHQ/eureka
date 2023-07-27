package com.legacycode.eureka.dex.inheritance

import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.test.TestArtifact
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class InheritanceJarParserTest {
  private val jarFile = TestArtifact("truth-1.1.5.jar").file

  @Test
  fun `it can build an inheritance adjacency list from a JAR`() {
    // given
    val adjacencyList = InheritanceArtifactParser.from(jarFile)
      .buildInheritanceTree()

    // when
    val graphvizTree = adjacencyList.tree(
      Ancestor("Lcom/google/common/truth/Subject;"),
      DotTreeBuilder("Truth Subjects"),
    )

    // then
    Approvals.verify(graphvizTree)
  }

  @Test
  fun `it should ignore anonymous inner classes`() {
    // given
    val adjacencyList = InheritanceArtifactParser.from(jarFile)
      .buildInheritanceTree()

    // when
    val graphvizTree = adjacencyList.tree(
      Ancestor("Ljava/lang/Object;"),
      DotTreeBuilder("Everything, all at once!"),
    )

    // then
    Approvals.verify(graphvizTree)
  }
}
