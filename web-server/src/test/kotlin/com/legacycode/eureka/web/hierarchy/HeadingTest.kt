package com.legacycode.eureka.web.hierarchy

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class HeadingTest {
  @Test
  fun `without prune keyword`() {
    // given
    val heading = Heading("wikipedia.apk", "java.lang.Object", null)

    // when & then
    assertThat(heading.displayText)
      .isEqualTo("wikipedia.apk (java.lang.Object)")
  }

  @Test
  fun `with prune keyword`() {
    // given
    val heading = Heading("wikipedia.apk", "java.lang.Object", "Table")

    // when & then
    assertThat(heading.displayText)
      .isEqualTo("""wikipedia.apk (java.lang.Object → showing "Table")""")
  }
}
