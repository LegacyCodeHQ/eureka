package com.legacycode.eureka.web.ownership

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class OwnershipServerTest {
  @Test
  fun `it uses the bundled d3 library`() {
    // given
    val ownershipHtml = OwnershipServerTest::class.java.classLoader
      .getResource("ownership.html")!!.readText()

    // when & then
    assertThat(ownershipHtml)
      .contains("<script src=\"/static/js/d3.v7.min.js\"></script>")
  }
}
