package io.redgreen.tumbleweed.cli.dev.diff

import com.google.common.truth.Truth.assertThat
import io.redgreen.tumbleweed.cli.dev.convert.from
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph.Node
import org.junit.jupiter.api.Test

class DiffTest {
  @Test
  fun `it returns an empty list when the baseline and implementation are same`() {
    // given
    val baseline = TestResource("01-baseline.csv").content
    val implementation = TestResource("01-implementation.csv").content

    val baselineGraph = BilevelEdgeBundlingGraph.from(baseline)
    val implementationGraph = BilevelEdgeBundlingGraph.from(implementation)

    // when
    val diff = baselineGraph - implementationGraph

    // then
    assertThat(diff.isEmpty())
      .isTrue()
  }

  @Test
  fun `it returns a list of missing nodes when the implementation is empty`() {
    // given
    val baseline = TestResource("02-baseline.csv").content
    val implementation = TestResource("02-implementation.csv").content

    val baselineGraph = BilevelEdgeBundlingGraph.from(baseline)
    val implementationGraph = BilevelEdgeBundlingGraph.from(implementation)

    // when
    val diff = baselineGraph - implementationGraph

    // then
    assertThat(diff.missing.nodes)
      .isEqualTo(
        listOf(
          Node("String name", 1),
          Node("void getName()", 2),
          Node("void setName(String)", 2),
        )
      )
  }
}

class TestResource(private val filename: String) {
  val content: String
    get() {
      val resourcePath = listOf("", "diff", filename).joinToString(System.getProperty("file.separator"))
      return TestResource::class.java.getResource(resourcePath)!!.readText()
    }
}
