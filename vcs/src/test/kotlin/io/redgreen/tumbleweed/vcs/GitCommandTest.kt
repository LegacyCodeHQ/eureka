package io.redgreen.tumbleweed.vcs

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class GitCommandTest {
  @Test
  fun `it should get the HEAD commit hash`() {
    // given
    val command = GitCommand("rev-parse", arrayOf("--short", "HEAD"), TestRepo("simple-android").path)

    // when
    val result = command.execute()

    // then
    assertThat(result.orNull())
      .isEqualTo(CommandResult(0, "5eb413173"))
  }
}
