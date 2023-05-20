package com.legacycode.tumbleweed.cli.dev.convert

import com.legacycode.tumbleweed.viz.edgebundling.EdgeBundlingGraph
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Command(
  name = "convert",
  description = ["(dev) converts a CSV file to an ObservableHQ JSON file"],
  hidden = true,
)
class ConvertCommand : Runnable {
  @Parameters(
    index = "0",
    description = ["path to the CSV file to convert"],
    arity = "1",
  )
  lateinit var csvFile: File

  override fun run() {
    val graph = EdgeBundlingGraph.from(csvFile.readText())
    println(graph.toJson())
  }
}
