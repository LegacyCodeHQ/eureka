package com.legacycode.eureka.dex.dependency

import com.legacycode.eureka.dex.test.TestApk
import org.approvaltests.Approvals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GraphBuilderTest {
  private lateinit var parser: DependencyApkParser

  @BeforeEach
  fun setUp() {
    val testApk = TestApk("wikipedia.apk")
    parser = DependencyApkParser(testApk.file)
  }

  @Test
  fun `build a graphviz graph`() {
    // when
    val adjacencyList = parser.buildDependencyGraph()

    // then
    val dependencyGraph = adjacencyList.graph(DotGraphBuilder("wikipedia.apk (Flows)"))
    Approvals.verify(dependencyGraph)
  }

  @Test
  fun `build a ontology graph`() {
    // when
    val adjacencyList = parser.buildDependencyGraph()

    // then
    val dependencyGraph = adjacencyList.graph(OntologyGraphBuilder())
    Approvals.verify(dependencyGraph)
  }
}
