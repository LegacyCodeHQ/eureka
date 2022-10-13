package io.redgreen.tumbleweed.cli.dev.json

import io.redgreen.tumbleweed.ClassScanner
import io.redgreen.tumbleweed.filesystem.CompiledClassFileFinder
import io.redgreen.tumbleweed.web.observablehq.graph
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

@Command(
  name = "json",
  description = ["(dev) generates ObservableHQ JSON for the class"],
)
class JsonCommand : Runnable {
  @Parameters(
    index = "0",
    description = ["uniquely identifiable (partially or fully) qualified class name"],
    arity = "1",
  )
  lateinit var className: String

  @Option(
    names = ["-b", "--buildDir"],
    description = ["path to the build directory"],
    required = false,
  )
  var buildDir: File? = null

  @Option(
    names = ["-c", "--check"],
    description = ["check the generated JSON for anomalies"],
    required = false,
  )
  var check: Boolean = false

  override fun run() {
    val classFilePath = CompiledClassFileFinder
      .find(className, (buildDir ?: File("")).absolutePath)
      ?: throw IllegalArgumentException("Class file not found for $className")

    val classStructure = ClassScanner.scan(classFilePath.toFile())
    if (check) {
      classStructure.check()
    } else {
      println(classStructure.graph.asJson())
    }
  }
}
