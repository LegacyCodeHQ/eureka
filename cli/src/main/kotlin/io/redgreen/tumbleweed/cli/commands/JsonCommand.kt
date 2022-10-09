package io.redgreen.tumbleweed.cli.commands

import io.redgreen.tumbleweed.ClassScanner
import io.redgreen.tumbleweed.filesystem.CompiledClassFileFinder
import io.redgreen.tumbleweed.web.observablehq.json
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

@Command(
  name = "json",
  description = ["generates ObservableHQ JSON for the class (for debugging)"],
  hidden = true,
)
class JsonCommand : Runnable {
  @Parameters(
    index = "0",
    description = ["uniquely identifiable (partially or fully) qualified name of the class"],
    arity = "1",
  )
  lateinit var className: String

  @Option(
    names = ["-b", "--buildDir"],
    description = ["path to the build directory"],
    required = false,
  )
  var buildDir: File? = null

  override fun run() {
    val classFilePath = CompiledClassFileFinder
      .find(className, (buildDir ?: File("")).absolutePath)
      ?: throw IllegalArgumentException("Class file not found for $className")

    println(ClassScanner.scan(classFilePath.toFile()).json)
  }
}
