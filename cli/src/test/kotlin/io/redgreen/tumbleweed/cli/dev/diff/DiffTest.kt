@file:Suppress("JUnitMalformedDeclaration")

package io.redgreen.tumbleweed.cli.dev.diff

import io.redgreen.tumbleweed.cli.dev.diff.EdgeBundlingGraphExtension.*
import io.redgreen.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(EdgeBundlingGraphExtension::class)
class DiffTest {
  @Test
  fun `it returns an empty list when the baseline and implementation are same`(
    @Graph("01-baseline.csv") baseline: BilevelEdgeBundlingGraph,
    @Graph("01-implementation.csv") implementation: BilevelEdgeBundlingGraph,
  ) {
    // when
    val diff = Diff.of(baseline, implementation)

    // then
    Approvals.verify(diff.report)
  }

  @Test
  fun `it returns a list of missing nodes when the implementation is empty`(
    @Graph("02-baseline.csv") baseline: BilevelEdgeBundlingGraph,
    @Graph("02-implementation.csv") implementation: BilevelEdgeBundlingGraph,
  ) {
    // when
    val diff = Diff.of(baseline, implementation)

    // then
    Approvals.verify(diff.report)
  }

  @Test
  fun `it returns a list of missing nodes when the implementation is missing some nodes`(
    @Graph("03-baseline.csv") baseline: BilevelEdgeBundlingGraph,
    @Graph("03-implementation.csv") implementation: BilevelEdgeBundlingGraph,
  ) {
    // when
    val diff = Diff.of(baseline, implementation)

    // then
    Approvals.verify(diff.report)
  }
}
