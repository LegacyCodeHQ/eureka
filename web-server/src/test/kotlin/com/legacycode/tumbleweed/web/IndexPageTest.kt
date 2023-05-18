package com.legacycode.tumbleweed.web

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class IndexPageTest {
  @Test
  fun `replace port number in an index HTML file`() {
    // given
    val indexPage = IndexPage.withPort(7997, "test-index-page.html")

    // when & then
    Approvals.verify(indexPage.content)
  }
}
