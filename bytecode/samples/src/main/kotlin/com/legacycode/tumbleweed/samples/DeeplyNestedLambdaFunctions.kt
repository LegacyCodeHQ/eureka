@file:Suppress("UnusedReceiverParameter", "unused", "UNUSED_PARAMETER", "SameParameterValue")

package com.legacycode.tumbleweed.samples

import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue

class DeeplyNestedLambdaFunctions {
  private val structureUpdatesQueue: Queue<String> = ArrayBlockingQueue<String>(10)

  private fun Application.setupRoutes(source: Source, port: Int) {
    routing {
      get("/") { serveIndexPage(port) }
      webSocket("/structure-updates") {
        openWsConnectionForStructureUpdates(structureUpdatesQueue, source)
      }
    }
  }

  private fun serveIndexPage(port: Int) {
    // nothing to see here
  }

  private fun openWsConnectionForStructureUpdates(
    structureUpdatesQueue: Queue<String>,
    source: Source,
  ) {
    // nothing to see here
  }
}

interface Source

private fun routing(block: () -> Unit) {
  // no-op
}

private fun get(url: String, block: () -> Unit) {
  // no-op
}

private fun webSocket(url: String, block: () -> Unit) {
  // no-op
}
