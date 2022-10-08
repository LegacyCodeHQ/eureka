package io.redgreen.tumbleweed.filesystem

import com.google.common.truth.Truth.assertThat
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile
import org.junit.jupiter.api.Test

class CompiledClassFileFinderTest {
  @Test
  fun `it should find the path of the class given the fully qualified name`() {
    // given & when
    val path = CompiledClassFileFinder.find("io.redgreen.tumbleweed.samples.FindMeJavaClass")

    // then
    assertThat(path.exists()).isTrue()
    assertThat(path.isRegularFile()).isTrue()
  }

  @Test
  fun `it should find the path of the class given the simple name`() {
    // given & when
    val path = CompiledClassFileFinder.find("FindMeJavaClass")

    // then
    assertThat(path.exists()).isTrue()
    assertThat(path.isRegularFile()).isTrue()
  }
}
