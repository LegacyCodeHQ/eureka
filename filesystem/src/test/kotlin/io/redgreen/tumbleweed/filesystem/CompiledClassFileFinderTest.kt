package io.redgreen.tumbleweed.filesystem

import com.google.common.truth.Truth.assertThat
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile
import org.junit.jupiter.api.Test

class CompiledClassFileFinderTest {
  @Test
  fun `it should find the path of the class given the fully qualified name`() {
    // given & when
    val path = CompiledClassFileFinder.find(
      className = "io.redgreen.tumbleweed.samples.FindMeJavaClass",
      searchDirectory = "../",
    )

    // then
    assertThat(path!!.exists()).isTrue()
    assertThat(path.isRegularFile()).isTrue()
  }

  @Test
  fun `it should find the path of the class given the simple name`() {
    // given & when
    val path = CompiledClassFileFinder.find(
      className = "FindMeJavaClass",
      searchDirectory = "../",
    )

    // then
    assertThat(path!!.exists()).isTrue()
    assertThat(path.isRegularFile()).isTrue()
  }

  @Test
  fun `it should find the path of the class given the simple name with package`() {
    // given & when
    val path = CompiledClassFileFinder.find(
      className = "samples.FindMeKotlinClass",
      searchDirectory = "../bytecode-samples/build/classes/kotlin/main",
    )

    // then
    assertThat(path!!.exists()).isTrue()
    assertThat(path.isRegularFile()).isTrue()
  }

  @Test
  fun `it should find the path of the exact class given multiple classes end with simple name`() {
    // given & when
    val classToSearch = "FindMeJavaClass"
    val path = CompiledClassFileFinder.find(
      className = classToSearch,
      searchDirectory = "../",
    )

    // then
    assertThat(path!!.exists()).isTrue()
    assertThat("$classToSearch.class" == path.fileName.toString()).isTrue()
    assertThat(path.isRegularFile()).isTrue()
  }

  @Test
  fun `it returns null if the class is not found`() {
    // given & when
    val path = CompiledClassFileFinder.find(
      className = "io.redgreen.tumbleweed.samples.MissingClass",
      searchDirectory = "src/test/resources",
    )

    // then
    assertThat(path).isNull()
  }
}
