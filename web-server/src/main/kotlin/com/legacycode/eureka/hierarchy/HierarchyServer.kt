package com.legacycode.eureka.hierarchy

import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.InheritanceAdjacencyList
import com.legacycode.eureka.dex.TreeClusterJsonTreeBuilder
import com.legacycode.eureka.hierarchy.HierarchyServer.Companion.PARAM_CLASS
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.uri
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File

class HierarchyServer(
  private val adjacencyList: InheritanceAdjacencyList,
  private val ancestorFromCommandLine: Ancestor,
  private val apkFile: File,
) {
  companion object {
    internal const val PARAM_CLASS = "class"
  }

  private lateinit var webServer: ApplicationEngine

  fun start(port: Int) {
    webServer = embeddedServer(
      Netty,
      port = port,
      module = { setupRoutes(adjacencyList, ancestorFromCommandLine, apkFile) }
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
      val className = call.parameters[PARAM_CLASS]
      val urlHasClassParameter = className != null

      if (urlHasClassParameter) {
        val root = Ancestor(toClassDescriptor(className!!))
        val treeClusterJson = adjacencyList.tree(root, TreeClusterJsonTreeBuilder())
        val html = getHierarchyHtml(getTitle(apkFile, root), getHeading(apkFile, root), treeClusterJson)
        call.respondText(html, ContentType.Text.Html)
      } else {
        val currentUrl = call.request.uri
        val redirectUrl = "$currentUrl?class=${ancestorFromCommandLine.fqn}"
        call.respondRedirect(redirectUrl)
        return@get
      }
    }
  }
}

private fun toClassDescriptor(className: String): String {
  return "L${className.replace('.', '/')};"
}

private fun getHierarchyHtml(title: String, heading: String, json: String): String {
  return HierarchyServer::class.java.getResourceAsStream("/hierarchy.html")!!
    .bufferedReader()
    .use { it.readText() }
    .replace("{{title}}", title)
    .replace("{{heading}}", heading)
    .replace("{{data}}", json)
}

private fun getTitle(apkFile: File, ancestor: Ancestor): String {
  val className = ancestor.fqn
  return "${apkFile.name} (${className.substring(className.lastIndexOf('.') + 1)})"
}

private fun getHeading(apkFile: File, ancestor: Ancestor): String {
  val className = ancestor.fqn
  return "${apkFile.name} (${className})"
}
