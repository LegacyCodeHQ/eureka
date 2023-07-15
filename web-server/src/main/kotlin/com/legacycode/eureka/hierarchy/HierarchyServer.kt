package com.legacycode.eureka.hierarchy

import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.InheritanceAdjacencyList
import com.legacycode.eureka.dex.TreeClusterJsonTreeBuilder
import io.ktor.server.application.Application
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File

class HierarchyServer(
  private val adjacencyList: InheritanceAdjacencyList,
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
  adjacencyList: InheritanceAdjacencyList,
  ancestorFromCommandLine: Ancestor,
  apkFile: File,
) {
  routing {
    get("/") {
      handleIndexRoute(
        adjacencyList,
        apkFile,
        ancestorFromCommandLine,
        HierarchyIndexPathEffects(this),
      )
    }
  }
}

private suspend fun handleIndexRoute(
  adjacencyList: InheritanceAdjacencyList,
  artifactFile: File,
  ancestorFromCommandLine: Ancestor,
  effects: HierarchyIndexPathEffects,
) {
  val className = effects.getClassParameter()
  val urlHasClassParameter = className != null

  if (!urlHasClassParameter) {
    val currentUrl = effects.getCurrentUrl()
    val redirectUrl = "$currentUrl?class=${ancestorFromCommandLine.fqn}"
    effects.redirectRequestTo(redirectUrl)
  } else {
    val root = Ancestor(toClassDescriptor(className!!))
    val searchTerm = effects.getPruneParameter()
    val activeAdjacencyList = if (searchTerm != null) {
      adjacencyList.prune(searchTerm)
    } else {
      adjacencyList
    }

    val html = HierarchyHtml(
      Title(artifactFile.name, root.fqn),
      Heading(artifactFile.name, root.fqn, searchTerm),
      activeAdjacencyList.tree(root, TreeClusterJsonTreeBuilder()),
    ).content
    effects.renderHtml(html)
  }
}

private fun toClassDescriptor(className: String): String {
  return "L${className.replace('.', '/')};"
}
