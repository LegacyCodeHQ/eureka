package com.legacycode.tumbleweed.web

import com.google.common.truth.Truth.assertThat
import kotlin.random.Random.Default.nextInt
import org.junit.jupiter.api.Test

class IndexPageTest {
  @Test
  fun `replace port number in an index HTML file`() {
    // given
    val aPort = nextInt(1000, 10_000)
    val indexPage = IndexPage.withPort(aPort)

    // when
    val content = indexPage.content

    // then
    assertThat(content)
      .contains("localhost:$aPort")
  }
}
