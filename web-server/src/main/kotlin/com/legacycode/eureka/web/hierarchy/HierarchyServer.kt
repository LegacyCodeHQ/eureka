package com.legacycode.eureka.web.hierarchy

import com.legacycode.eureka.dex.AdjacencyList
import com.legacycode.eureka.dex.Ancestor
import io.ktor.server.application.Application
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File

class HierarchyServer(
  private val adjacencyList: AdjacencyList,
  private val ancestorFromCommandLine: Ancestor,
  private val artifactFile: File,
) {
  private lateinit var webServer: ApplicationEngine

  fun start(port: Int) {
    webServer = embeddedServer(
      Netty,
      port = port,
      module = { setupRoutes(adjacencyList, ancestorFromCommandLine, artifactFile) }
    ).start(wait = true)
  }
}

fun Application.setupRoutes(
  adjacencyList: AdjacencyList,
  ancestorFromCommandLine: Ancestor,
  artifactFile: File,
) {
  val hierarchyIndexController = HierarchyIndexController(
    artifactFile.name,
    adjacencyList,
    ancestorFromCommandLine.fqn
  )

  routing {
    staticResources("", null)
    get("/") {
      hierarchyIndexController.handleRequest(HierarchyIndexPathEffects(this))
    }
  }
}
