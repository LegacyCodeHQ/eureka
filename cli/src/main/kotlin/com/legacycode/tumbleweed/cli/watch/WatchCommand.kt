package com.legacycode.tumbleweed.cli.watch

import com.legacycode.tumbleweed.android.AndroidMemberClassifier
import com.legacycode.tumbleweed.cli.DEFAULT_PORT
import com.legacycode.tumbleweed.cli.watch.WatchCommand.Experiment.android
import com.legacycode.tumbleweed.filesystem.CompiledClassFileFinder
import com.legacycode.tumbleweed.web.CompiledClassFile
import com.legacycode.tumbleweed.web.TumbleweedServer
import com.legacycode.tumbleweed.web.observablehq.classifiers.BasicMemberClassifier
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

@Command(
  name = "watch",
  description = ["visualize real-time JVM class structure in your browser"],
)
class WatchCommand : Runnable {
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
    names = ["-p", "--port"],
    description = ["the server port number"],
    defaultValue = "$DEFAULT_PORT",
    required = false,
  )
  var port: Int = DEFAULT_PORT

  @Suppress("EnumNaming")
  enum class Experiment {
    android,
  }

  @Option(
    names = ["-x", "--experiment"],
    description = ["available features: ${'$'}{COMPLETION-CANDIDATES}"],
    required = false,
  )
  private var experiment: Experiment? = null

  override fun run() {
    val classFilePath = CompiledClassFileFinder
      .find(className, (buildDir ?: File("")).absolutePath)
      ?: throw IllegalArgumentException("Class file not found for $className")

    val classifier = if (experiment == android) {
      AndroidMemberClassifier()
    } else {
      BasicMemberClassifier()
    }

    TumbleweedServer().start(CompiledClassFile(classFilePath.toFile(), classifier), port)
  }
}
