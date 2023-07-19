package com.legacycode.eureka.dex.dependency

import com.legacycode.eureka.dex.test.TestApk
import com.legacycode.eureka.dex.test.TestGraphBuilder
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class DependencyApkParserTest {
  @Test
  fun name() {
    // given
    val testApk = TestApk("wikipedia.apk")
    val parser = DependencyApkParser(testApk.file)

    // when
    val adjacencyList = parser.buildDependencyGraph()

    // then
    val dependencyGraph = adjacencyList.graph(TestGraphBuilder())
    Approvals.verify(dependencyGraph)
  }
}
