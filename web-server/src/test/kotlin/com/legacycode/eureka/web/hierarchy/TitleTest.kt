package com.legacycode.eureka.web.hierarchy

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class TitleTest {
  @Test
  fun displayText() {
    // given
    val title = Title("junit-runtime.jar", "org.junit.jupiter.api.Test")

    // when & then
    assertThat(title.displayText)
      .isEqualTo("junit-runtime.jar (Test)")
  }
}
