package com.legacycode.eureka.cli.modules

import com.legacycode.eureka.gradle.runGradleCommands
import java.io.File
import kotlin.system.measureTimeMillis
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Command(
  name = "modules",
  description = ["create a PlantUML component diagram for a Gradle project"],
)
class ModulesCommand : Runnable {
  @Parameters(
    index = "0",
    description = ["path to the Gradle project"],
    arity = "1",
  )
  lateinit var projectRoot: File

  override fun run() {
    if (!projectRoot.isGradleProject()) {
      println("Ouch... does the path contain a Gradle project? ($projectRoot)")
    } else {
      var graphviz: String
      val timeMillis = measureTimeMillis {
        graphviz = runGradleCommands(projectRoot)
      }
      println("Execution took: ${timeMillis}ms")
      println()
      println(graphviz)
    }

    println("Copy and paste the output at https://dreampuf.github.io/GraphvizOnline")
  }

  private fun File.isGradleProject(): Boolean =
    this.resolve("gradlew").exists()
}
