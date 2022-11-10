@file:Suppress("unused", "UNUSED_PARAMETER")

package io.redgreen.tumbleweed.samples

class ExtensionFunctionsWithReceivers {
  private val defaultPort = 7070

  fun start(port: Int) {
    embeddedServer {
      installWebSockets(defaultPort)
    }
  }

  private fun Application.installWebSockets(defaultPort: Int) {
    println("Starting $this in port $defaultPort")
  }
}

fun embeddedServer(block: Application.() -> Unit) {
  block(Application())
}

class Application
