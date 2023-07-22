package com.legacycode.eureka.hierarchy

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class HierarchyServerTest {
  @Test
  fun `it uses the bundled d3 library`() {
    // given
    val hierarchyHtml = HierarchyServerTest::class.java.classLoader
      .getResource("hierarchy.html")!!.readText()

    // when & then
    assertThat(hierarchyHtml)
      .contains("<script src=\"/static/js/d3.v7.min.js\"></script>")
  }
}
