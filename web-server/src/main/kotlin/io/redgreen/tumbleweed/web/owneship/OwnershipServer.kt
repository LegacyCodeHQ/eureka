package io.redgreen.tumbleweed.web.owneship

import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.redgreen.tumbleweed.vcs.Repo
import io.redgreen.tumbleweed.vcs.RepoFile
import io.redgreen.tumbleweed.vcs.blame.BlameCommand
import io.redgreen.tumbleweed.vcs.blame.observablehq.OwnershipTreemapJson
import io.redgreen.tumbleweed.web.owneship.OwnershipServer.Companion.PARAM_FILE
import org.slf4j.LoggerFactory

class OwnershipServer {
  companion object {
    internal const val PARAM_FILE = "file"
  }

  private val logger = LoggerFactory.getLogger(OwnershipServer::class.java)

  private lateinit var webServer: ApplicationEngine

  fun start(path: String, port: Int) {
    logger.info("Starting web server @ http://localhost:{}", port)
    webServer = embeddedServer(
      Netty,
      port = port,
      module = { setupRoutes(Repo(path)) }
    ).start(wait = true)
  }
}

fun Application.setupRoutes(repo: Repo) {
  routing {
    get("/") {
      val ownershipTreemap = ownershipTreemapJson(
        repo,
        call.parameters[PARAM_FILE]!!,
      )
      call.respondText(ownershipTreemap.toJson(), ContentType.Application.Json)
    }
  }
}

private fun ownershipTreemapJson(repo: Repo, filePath: String): OwnershipTreemapJson {
  val blameCommand = BlameCommand(repo, RepoFile(filePath))
  val blameResult = blameCommand.execute().orNull()!!
  return OwnershipTreemapJson.from(blameResult)
}
