package com.legacycode.eureka.web.hierarchy

import com.legacycode.eureka.web.HtmlTemplate
import com.legacycode.eureka.web.Placeholder

class HierarchyHtml(
  private val title: Title,
  private val heading: Heading,
  private val jsonData: String,
) {
  val content: String
    get() {
      return HtmlTemplate
        .fromResource("/hierarchy.html")
        .bind(Placeholder("title"), title.displayText)
        .bind(Placeholder("heading"), heading.displayText)
        .bind(Placeholder("data"), jsonData)
        .content
    }
}
