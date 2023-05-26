package com.legacycode.tumbleweed.cli.modules

import java.io.File
import kotlin.system.measureTimeMillis
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import runGradleCommands

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
    var uml: String
    val timeMillis = measureTimeMillis {
      uml = runGradleCommands(projectRoot)
    }
    println("Execution took: ${timeMillis}ms")
    println()
    println(uml)
  }
}
