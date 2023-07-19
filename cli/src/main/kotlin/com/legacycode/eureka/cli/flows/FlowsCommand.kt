package com.legacycode.eureka.cli.flows

import com.legacycode.eureka.dex.dependency.DependencyApkParser
import com.legacycode.eureka.dex.dependency.DotGraphBuilder
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Command(
  name = "flows",
  description = ["prints a GraphViz output containing navigation flows in an APK"],
)
class FlowsCommand : Runnable {
  @Parameters(
    index = "0",
    description = ["path to an Android .apk file"],
    arity = "1",
  )
  private lateinit var apkFile: File

  override fun run() {
    val dependencyAdjacencyList = DependencyApkParser(apkFile).buildDependencyGraph()
    val graphBuilder = DotGraphBuilder("${apkFile.name} (flows)")

    val graphVizOutput = dependencyAdjacencyList.graph(graphBuilder)
    println(graphVizOutput)

    println("Copy and paste the output at https://dreampuf.github.io/GraphvizOnline")
  }
}
