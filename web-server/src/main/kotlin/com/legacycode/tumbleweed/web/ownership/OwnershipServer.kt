package com.legacycode.tumbleweed.web.ownership

import com.legacycode.tumbleweed.vcs.Repo
import com.legacycode.tumbleweed.vcs.RepoFile
import com.legacycode.tumbleweed.vcs.blame.BlameCommand
import com.legacycode.tumbleweed.vcs.blame.observablehq.OwnershipTreemapJson
import com.legacycode.tumbleweed.web.ownership.OwnershipServer.Companion.PARAM_FILE
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File
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
      module = { setupRoutes(Repo(path), port) }
    ).start(wait = true)
  }
}

fun Application.setupRoutes(repo: Repo, port: Int) {
  routing {
    get("/") {
      val filePath = call.parameters[PARAM_FILE]

      val html = if (filePath != null) {
        val ownershipTreemapJson = ownershipTreemapJson(repo, filePath).toJson()
        val treemapHtml = getTreemapHtml(ownershipTreemapJson)
        treemapHtml
      } else {
        showFilesHtml(repo, port)
      }
      call.respondText(html, ContentType.Text.Html)
    }
  }
}

private fun showFilesHtml(repo: Repo, port: Int): String {
  val repoDirectory = File(repo.path)
  val filePaths = ListFilesGitCommand(repoDirectory).execute()

  val filePathLinks = filePaths
    .joinToString("\n") { path ->
      """  <div><a href="${ownershipFilePathUrl(path, port)}" target="_blank">$path</a></div>"""
    }

  val repoDirectoryName = getRepoName(repoDirectory)

  return """
    <html>
    <head>
      <title>Ownership · $repoDirectoryName</title>
      <style>
        body {
          font-family: sans-serif;
          font-size: medium;
        }

      </style>
    </head>
    <body>
      <h1>$repoDirectoryName (${filePaths.size})</h1>
    $filePathLinks
    </body>
    </html>
  """.trimIndent()
}

private fun getRepoName(repoDirectory: File): String {
  return if (repoDirectory.absoluteFile.name == ".git") {
    repoDirectory.absoluteFile.parentFile.name
  } else {
    repoDirectory.absoluteFile.name
  }
}

private fun ownershipFilePathUrl(
  path: String,
  port: Int,
): String {
  return "http://localhost:$port?file=$path"
}

private fun ownershipTreemapJson(
  repo: Repo,
  filePath: String,
): OwnershipTreemapJson {
  val blameCommand = BlameCommand(repo, RepoFile(filePath))
  val blameResult = blameCommand.execute().orNull()!!
  return OwnershipTreemapJson.from(blameResult)
}

private fun getTreemapHtml(json: String): String {
  return OwnershipServer::class.java.getResourceAsStream("/treemap.html")!!
    .bufferedReader()
    .use { it.readText() }
    .replace("{{treemap-data}}", json)
}
