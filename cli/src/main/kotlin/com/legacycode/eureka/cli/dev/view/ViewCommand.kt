package com.legacycode.eureka.cli.dev.view

import com.legacycode.eureka.cli.DEFAULT_PORT
import com.legacycode.eureka.viz.edgebundling.EdgeBundlingGraph
import com.legacycode.eureka.viz.edgebundling.JsonFile
import com.legacycode.eureka.web.WatchServer
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

@Command(
  name = "view",
  description = ["(dev) visualize a ObservableHQ JSON file in your browser"],
  hidden = true,
)
class ViewCommand : Runnable {
  @Parameters(
    index = "0",
    description = ["path to the JSON file to visualize"],
    arity = "1",
  )
  lateinit var jsonFile: File

  @Option(
    names = ["-p", "--port"],
    description = ["port number to run the server on"],
    defaultValue = "$DEFAULT_PORT",
    required = false,
  )
  var port: Int = DEFAULT_PORT

  override fun run() {
    if (!jsonFile.exists() && !jsonFile.isFile) {
      println("❌ JSON file not found at given path: $jsonFile, please provide a valid file path")
    } else if (!EdgeBundlingGraph.isValidJson(jsonFile.readText())) {
      println("❌ Provided JSON file is not valid. Please provide a valid JSON file")
    } else {
      WatchServer().start(JsonFile(jsonFile), port)
    }
  }
}
