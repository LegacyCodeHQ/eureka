package com.legacycode.eureka.web.hierarchy

import com.google.common.truth.Truth.assertThat
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class HierarchyHtmlTest {
  @Test
  fun content() {
    // given
    val title = Title("junit-runtime.jar", "java.lang.Object")
    val heading = Heading("junit-runtime.jar", "java.lang.Object", null)
    val jsonData = """{"name": "java.lang.Object"}"""
    val hierarchyHtml = HierarchyHtml(title, heading, jsonData)

    // when
    val content = hierarchyHtml.content

    // then
    Approvals.verify(content)
  }

  @Test
  fun `it uses the bundled d3 library`() {
    // given
    val title = Title("junit-runtime.jar", "java.lang.Object")
    val heading = Heading("junit-runtime.jar", "java.lang.Object", null)
    val jsonData = """{"name": "java.lang.Object"}"""
    val hierarchyHtml = HierarchyHtml(title, heading, jsonData)

    // when
    val content = hierarchyHtml.content

    // then
    assertThat(content)
      .contains("<script src=\"/static/js/d3.v7.min.js\"></script>")
  }
}
