package com.legacycode.tumbleweed.cli.dev.diff

import com.legacycode.tumbleweed.web.observablehq.BilevelEdgeBundlingGraph
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
  name = "diff",
  description = ["(dev) compare two ObservableHQ JSON files"],
  hidden = true,
)
class DiffCommand : Runnable {
  @Option(
    names = ["-b", "--baseline"],
    description = ["path to the baseline JSON file"],
    required = true,
  )
  lateinit var baselineJson: File

  @Option(
    names = ["-i", "--implementation"],
    description = ["path to the implementation JSON file"],
    required = true,
  )
  lateinit var implementationJson: File

  override fun run() {
    val baseline = BilevelEdgeBundlingGraph.fromJson(baselineJson.readText())
    val implementation = BilevelEdgeBundlingGraph.fromJson(implementationJson.readText())

    val diff = Diff.of(baseline, implementation)

    println(diff.report)
  }
}
