package com.legacycode.eureka.testing

import com.google.common.truth.Truth.assertThat
import java.io.FileNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TextResourceTest {
  @Test
  fun `it can read contents of a text resource file`() {
    // given
    val resource = TextResource("/hello-world.txt")

    // when & then
    assertThat(resource.content)
      .isEqualTo("Hello, testing world!\n")
  }

  @Test
  fun `it should throw an exception when the file does not exist`() {
    // given
    val resource = TextResource("404-file.txt")

    // when & then
    val exception = assertThrows<FileNotFoundException> {
      resource.content
    }
    assertThat(exception.message)
      .isEqualTo("Unable to find file: '404-file.txt'")
  }
}
