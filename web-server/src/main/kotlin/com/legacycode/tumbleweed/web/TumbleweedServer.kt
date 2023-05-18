package com.legacycode.tumbleweed.web

import com.legacycode.tumbleweed.filesystem.FileWatcher
import com.legacycode.tumbleweed.version.TwdProperties
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.http.content.staticBasePackage
import io.ktor.server.netty.Netty
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import io.ktor.util.pipeline.PipelineContext
import io.ktor.websocket.Frame
import java.time.Duration
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

class TumbleweedServer {
  private val logger = LoggerFactory.getLogger(TumbleweedServer::class.java)

  private lateinit var webServer: ApplicationEngine

  private val structureUpdatesQueue = LinkedBlockingQueue<String>()

  private val classFileChangesWatcher = FileWatcher()

  fun start(source: GraphDataSource, port: Int) {
    startWatchingFileForChanges(source)

    logger.info("Starting web server @ http://localhost:{}", port)
    webServer = embeddedServer(Netty, port = port) {
      installWebSockets()
      setupRoutes(source, port)
    }.start(wait = true)
  }

  fun stop() {
    logger.info("Stopping web server…")
    webServer.stop(0, 0)
  }

  private fun Application.installWebSockets() {
    install(WebSockets) {
      pingPeriod = Duration.ofSeconds(15)
      timeout = Duration.ofSeconds(15)
      maxFrameSize = Long.MAX_VALUE
      masking = false
    }
  }

  private fun Application.setupRoutes(source: GraphDataSource, port: Int) {
    routing {
      get("/") { serveIndexPage(port) }
      get("/version") { serveVersionText() }
      webSocket("/structure-updates") {
        openWsConnectionForStructureUpdates(structureUpdatesQueue, source)
      }
      static("/static") {
        staticBasePackage = "static"
        resources("")
      }
    }
  }

  private suspend fun PipelineContext<Unit, ApplicationCall>.serveVersionText() {
    call.respond(TwdProperties.get().version.name)
  }

  private suspend fun PipelineContext<Unit, ApplicationCall>.serveIndexPage(port: Int) {
    val indexPage = IndexPage.withPort(port)
    call.respondText(indexPage.content, indexPage.contentType)
  }

  private suspend fun DefaultWebSocketServerSession.openWsConnectionForStructureUpdates(
    messageQueue: BlockingQueue<String>,
    source: GraphDataSource,
  ) {
    logger.info("Web socket connection opened. Ready to send updates.")
    send(Frame.Text(source.graph.toJson()))

    while (true) {
      val message = withContext(Dispatchers.IO) {
        messageQueue.take()
      }
      logger.info("Sending updated structure to client")
      send(Frame.Text(message))
    }
  }

  private fun startWatchingFileForChanges(source: GraphDataSource) {
    val location = source.location

    logger.info("Watching for changes: {}", location)

    classFileChangesWatcher.startWatching(location.toPath()) {
      if (!location.exists()) {
        logger.error("Source file does not exist: {}", location)
      } else {
        structureUpdatesQueue.add(source.graph.toJson())
      }
    }
  }
}
