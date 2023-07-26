package com.legacycode.eureka.web.flows

import com.legacycode.eureka.dex.dependency.D3GraphBuilder
import com.legacycode.eureka.dex.dependency.DependencyApkParser
import com.legacycode.eureka.web.HtmlTemplate
import com.legacycode.eureka.web.Placeholder
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File

class FlowsServer(private val apkFile: File) {
  private lateinit var webServer: ApplicationEngine

  fun start(port: Int) {
    webServer = embeddedServer(
      Netty,
      port = port,
      module = { setupRoutes(apkFile) },
    ).start(wait = true)
  }
}

fun Application.setupRoutes(apkFile: File) {
  routing {
    staticResources("", null)
    get("/") {
      val parser = DependencyApkParser(apkFile)
      val dependencyGraph = parser.buildDependencyGraph()
      val graphJson = dependencyGraph.graph(D3GraphBuilder())

      val displayName = "Flows • ${apkFile.name}"

      val flowsHtml = HtmlTemplate
        .fromResource("/flows.html")
        .bind(Placeholder("data"), graphJson)
        .bind(Placeholder("title"), displayName)
        .bind(Placeholder("heading"), displayName)
        .content

      this.call.respondText(flowsHtml, ContentType.Text.Html)
    }
  }
}
