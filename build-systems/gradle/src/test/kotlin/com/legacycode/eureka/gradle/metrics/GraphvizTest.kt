package com.legacycode.eureka.gradle.metrics

import com.legacycode.eureka.gradle.graphvizOutput
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class GraphvizTest {
  @Test
  fun `print graphviz dsl`() {
    // given
    val (projectStructure, subprojectDependencies) = eureka

    // when
    val graphviz = graphvizOutput(projectStructure, subprojectDependencies)

    // then
    Approvals.verify(graphviz)
  }
}
