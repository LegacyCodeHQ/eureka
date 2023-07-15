package com.legacycode.eureka.hierarchy

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class HierarchyHtmlTest {
  @Test
  fun content() {
    // given
    val filename = "junit-runtime.jar"
    val className = "java.lang.Object"
    val jsonData = """{"name": "java.lang.Object"}"""

    val hierarchyHtml = HierarchyHtml(Title(filename, className), Heading(filename, className, null), jsonData)

    // when
    val content = hierarchyHtml.content

    // then
    Approvals.verify(content)
  }
}
