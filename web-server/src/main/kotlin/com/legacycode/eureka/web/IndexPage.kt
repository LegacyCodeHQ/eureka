package com.legacycode.eureka.web

import com.legacycode.eureka.Experiment
import io.ktor.http.ContentType

class IndexPage private constructor(
  private val port: Int,
  private val activeExperiment: Experiment?,
) {
  companion object {
    private const val INDEX_FILENAME = "index.html"
    private const val MIME_TYPE_HTML = "text/html"

    private const val HOST = "localhost"
    private const val DEFAULT_PORT = 7070
    private const val HOST_PORT_IN_HTML = "$HOST:$DEFAULT_PORT"

    fun newInstance(
      port: Int,
      activeExperiment: Experiment? = null,
    ): IndexPage =
      IndexPage(port, activeExperiment)
  }

  val content: String by lazy {
    val indexPageContent = IndexPage::class.java.classLoader
      .getResourceAsStream(INDEX_FILENAME)!!
      .bufferedReader()
      .readText()

    val modifiedContent = indexPageContent
      .replace(HOST_PORT_IN_HTML, "$HOST:$port")

    return@lazy if (activeExperiment != null) {
      modifiedContent.replace("data-experiment=\"none\"", "data-experiment=\"$activeExperiment\"")
    } else {
      modifiedContent
    }
  }

  val contentType: ContentType by lazy {
    ContentType.parse(MIME_TYPE_HTML)
  }
}
