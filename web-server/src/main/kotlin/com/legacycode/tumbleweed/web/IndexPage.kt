package com.legacycode.tumbleweed.web

import io.ktor.http.ContentType

class IndexPage(
  private val port: Int,
) {
  companion object {
    private const val INDEX_PAGE_FILE = "index.html"
    private const val MIME_TYPE_HTML = "text/html"

    private const val HOST = "localhost"
    private const val DEFAULT_PORT = 7070
    private const val HOST_PORT_IN_HTML = "$HOST:$DEFAULT_PORT"

    fun withPort(port: Int): IndexPage =
      IndexPage(port)
  }

  val content: String by lazy {
    val indexPageContent = IndexPage::class.java.classLoader
      .getResourceAsStream(INDEX_PAGE_FILE)!!
      .bufferedReader()
      .readText()

    indexPageContent.replace(HOST_PORT_IN_HTML, "$HOST:$port")
  }

  val contentType: ContentType by lazy {
    ContentType.parse(MIME_TYPE_HTML)
  }
}
