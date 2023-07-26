package com.legacycode.eureka.cli.flows

import com.legacycode.eureka.web.flows.FlowsServer
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Command(
  name = "flows",
  description = ["visualize navigation flows in an APK"],
)
class FlowsCommand : Runnable {
  companion object {
    private const val DEFAULT_PORT = 7060
  }

  @Parameters(
    index = "0",
    description = ["path to an Android .apk file"],
    arity = "1",
  )
  private lateinit var apkFile: File

  override fun run() {
    FlowsServer(apkFile).start(DEFAULT_PORT)
  }
}
