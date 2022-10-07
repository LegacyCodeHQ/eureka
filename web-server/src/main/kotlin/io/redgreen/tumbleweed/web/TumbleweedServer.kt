package io.redgreen.tumbleweed.web

import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
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
import io.redgreen.tumbleweed.ClassFileLocation
import io.redgreen.tumbleweed.ClassScanner
import io.redgreen.tumbleweed.filesystem.FileWatcher
import io.redgreen.tumbleweed.web.observablehq.json
import java.time.Duration
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

class TumbleweedServer {
  private val logger = LoggerFactory.getLogger(TumbleweedServer::class.java)

  private lateinit var webServer: ApplicationEngine

  private val indexHtml: String
    get() {
      return TumbleweedServer::class.java.classLoader.getResourceAsStream("index.html")!!
        .bufferedReader()
        .use { it.readText() }
    }

  private val structureUpdatesQueue = LinkedBlockingQueue<String>()

  private val classFileChangesWatcher = FileWatcher()

  fun start(port: Int) {
    val classFileLocation = ClassFileLocation(
      "./bytecode-samples/build/classes/kotlin/main",
      "io.redgreen.tumbleweed.samples.ClassWithMethodsCallingMethods"
    )
    val classFilePath = classFileLocation.file.toPath()
    startWatchingClassFileForChanges(classFileLocation)

    logger.info("Starting web server @ http://localhost:{}", port)
    webServer = embeddedServer(Netty, port = port) {
      installWebSockets()
      setupRoutes()
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

  private fun Application.setupRoutes() {
    routing {
      get("/") { serveIndexPage() }
      webSocket("/structure-updates") { openWsConnectionForStructureUpdates(structureUpdatesQueue) }
    }
  }

  private suspend fun PipelineContext<Unit, ApplicationCall>.serveIndexPage() {
    call.respondText(indexHtml, ContentType.parse("text/html"))
  }

  private suspend fun DefaultWebSocketServerSession.openWsConnectionForStructureUpdates(
    messageQueue: BlockingQueue<String>,
  ) {
    logger.info("Web socket connection opened. Ready to send updates.")
    while (true) {
      val message = withContext(Dispatchers.IO) {
        messageQueue.take()
      }
      send(Frame.Text(message))
    }
  }

  private fun startWatchingClassFileForChanges(classFileLocation: ClassFileLocation) {
    val watchedClassFile = classFileLocation.file
    logger.info("Watching class file for changes: {}", watchedClassFile)
    if (!watchedClassFile.exists()) {
      logger.error("Class file does not exist: {}", watchedClassFile)
      return
    }

    classFileChangesWatcher.startWatching(watchedClassFile.toPath()) {
      structureUpdatesQueue.add(ClassScanner.scan(classFileLocation).json)
    }
  }
}
