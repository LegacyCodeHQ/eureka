package io.redgreen.tumbleweed.web

import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

class TumbleweedServer {
  private lateinit var webServer: ApplicationEngine

  fun start() {
    val port = 7070
    println("Starting web server @ http://localhost:$port")
    webServer = embeddedServer(Netty, port = port) {
      routing {
        get("/") { call.respond("Hello, world!") }
      }
    }.start(wait = true)
  }

  fun stop() {
    println("Stopping web server...")
    webServer.stop(0, 0)
  }
}
