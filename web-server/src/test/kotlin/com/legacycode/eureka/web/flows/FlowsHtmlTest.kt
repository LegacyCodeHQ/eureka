package com.legacycode.eureka.web.flows

import com.google.common.truth.Truth
import com.legacycode.eureka.testing.TextResource
import org.junit.jupiter.api.Test

class FlowsHtmlTest {
  @Test
  fun `it uses the bundled d3 library`() {
    // given
    val ownershipHtml = TextResource("flows.html").content

    // when & then
    Truth.assertThat(ownershipHtml)
      .contains("<script src=\"/static/js/d3.v7.min.js\"></script>")
  }
}
