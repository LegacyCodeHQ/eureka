package com.legacycode.eureka.hierarchy

import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.InheritanceAdjacencyList
import com.legacycode.eureka.dex.TreeClusterJsonTreeBuilder
import com.legacycode.eureka.hierarchy.HierarchyServer.Companion.PARAM_CLASS
import com.legacycode.eureka.hierarchy.HierarchyServer.Companion.PARAM_PRUNE
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.uri
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.PipelineContext
import java.io.File

class HierarchyServer(
  private val adjacencyList: InheritanceAdjacencyList,
  private val ancestorFromCommandLine: Ancestor,
  private val artifactFile: File,
) {
  companion object {
    internal const val PARAM_CLASS = "class"
    internal const val PARAM_PRUNE = "prune"
  }

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
      handleIndexRoute(this, adjacencyList, apkFile, ancestorFromCommandLine)
    }
  }
}

private suspend fun handleIndexRoute(
  context: PipelineContext<Unit, ApplicationCall>,
  adjacencyList: InheritanceAdjacencyList,
  artifactFile: File,
  ancestorFromCommandLine: Ancestor,
) {
  val className = context.call.parameters[PARAM_CLASS]
  val urlHasClassParameter = className != null

  if (urlHasClassParameter) {
    val root = Ancestor(toClassDescriptor(className!!))
    val searchTerm = context.call.parameters[PARAM_PRUNE]
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
    context.call.respondText(html, ContentType.Text.Html)
  } else {
    val currentUrl = context.call.request.uri
    val redirectUrl = "$currentUrl?class=${ancestorFromCommandLine.fqn}"
    context.call.respondRedirect(redirectUrl)
  }
}

private fun toClassDescriptor(className: String): String {
  return "L${className.replace('.', '/')};"
}
