@file:Suppress("unused", "UNUSED_PARAMETER")

package io.redgreen.tumbleweed.samples

class ExtensionFunctionsWithReceivers {
  fun start(port: Int) {
    embeddedServer {
      installWebSockets()
    }
  }

  private fun Application.installWebSockets() {
    println(this)
  }
}

fun embeddedServer(block: Application.() -> Unit) {
  block(Application())
}

class Application
