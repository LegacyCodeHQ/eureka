package com.legacycode.tumbleweed.web

import io.ktor.http.ContentType

class IndexPage private constructor(
  private val port: Int,
  private val resourceFilename: String,
) {
  companion object {
    private const val INDEX_FILENAME = "index.html"
    private const val MIME_TYPE_HTML = "text/html"

    private const val HOST = "localhost"
    private const val DEFAULT_PORT = 7070
    private const val HOST_PORT_IN_HTML = "$HOST:$DEFAULT_PORT"

    fun withPort(
      port: Int,
      resourceFilename: String = INDEX_FILENAME,
    ): IndexPage =
      IndexPage(port, resourceFilename)
  }

  val content: String by lazy {
    val indexPageContent = IndexPage::class.java.classLoader
      .getResourceAsStream(resourceFilename)!!
      .bufferedReader()
      .readText()

    indexPageContent.replace(HOST_PORT_IN_HTML, "$HOST:$port")
  }

  val contentType: ContentType by lazy {
    ContentType.parse(MIME_TYPE_HTML)
  }
}
