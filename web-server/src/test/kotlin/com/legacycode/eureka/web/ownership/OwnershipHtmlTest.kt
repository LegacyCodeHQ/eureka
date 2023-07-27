package com.legacycode.eureka.web.ownership

import com.google.common.truth.Truth.assertThat
import com.legacycode.eureka.testing.TextResource
import org.junit.jupiter.api.Test

class OwnershipHtmlTest {
  @Test
  fun `it uses the bundled d3 library`() {
    // given
    val ownershipHtml = TextResource("ownership.html").content

    // when & then
    assertThat(ownershipHtml)
      .contains("<script src=\"/static/js/d3.v7.min.js\"></script>")
  }
}
