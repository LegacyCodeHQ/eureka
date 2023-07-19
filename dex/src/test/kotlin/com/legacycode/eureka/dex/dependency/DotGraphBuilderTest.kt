package com.legacycode.eureka.dex.dependency

import com.legacycode.eureka.dex.test.TestApk
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class DotGraphBuilderTest {
  @Test
  fun `build a graphviz graph`() {
    // given
    val testApk = TestApk("wikipedia.apk")
    val parser = DependencyApkParser(testApk.file)

    // when
    val adjacencyList = parser.buildDependencyGraph()

    // then
    val dependencyGraph = adjacencyList.graph(DotGraphBuilder("wikipedia.apk (Fragments)"))
    Approvals.verify(dependencyGraph)
  }
}
