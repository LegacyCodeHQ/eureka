package io.redgreen.tumbleweed.cli.dev.diff

import com.google.common.truth.Truth.assertThat
import io.redgreen.tumbleweed.cli.dev.convert.from
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
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
}

class TestResource(private val filename: String) {
  val content: String
    get() {
      val resourcePath = listOf("", "diff", filename).joinToString(System.getProperty("file.separator"))
      return TestResource::class.java.getResource(resourcePath)!!.readText()
    }
}
