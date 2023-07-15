package com.legacycode.eureka.hierarchy

import io.ktor.http.ContentType
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.uri
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.util.pipeline.PipelineContext

class HierarchyDefaultPathEffects(private val context: PipelineContext<Unit, ApplicationCall>) {
  companion object {
    private const val PARAM_CLASS = "class"
    private const val PARAM_PRUNE = "prune"
  }

  fun getCurrentUrl(): String {
    return context.call.request.uri
  }

  fun getClassParameter(): String? {
    return context.call.parameters[PARAM_CLASS]
  }

  fun getPruneParameter(): String? {
    return context.call.parameters[PARAM_PRUNE]
  }

  suspend fun redirectRequestTo(url: String) {
    context.call.respondRedirect(url)
  }

  suspend fun renderHtml(html: String) {
    context.call.respondText(html, ContentType.Text.Html)
  }
}
