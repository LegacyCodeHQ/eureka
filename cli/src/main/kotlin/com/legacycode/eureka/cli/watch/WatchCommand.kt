package com.legacycode.eureka.cli.watch

import com.legacycode.eureka.Experiment
import com.legacycode.eureka.Experiment.android
import com.legacycode.eureka.android.AndroidMemberClassifier
import com.legacycode.eureka.cli.DEFAULT_PORT
import com.legacycode.eureka.filesystem.CompiledClassFileFinder
import com.legacycode.eureka.viz.edgebundling.BasicMemberClassifier
import com.legacycode.eureka.viz.edgebundling.CompiledClassFile
import com.legacycode.eureka.web.TumbleweedServer
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

    TumbleweedServer(experiment).start(CompiledClassFile(classFilePath.toFile(), classifier), port)
  }
}
