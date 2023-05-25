package com.legacycode.ureka.gradle.commands

import com.google.common.truth.Truth.assertThat
import java.io.File
import org.junit.jupiter.api.Test

class GradleProjectsCommandTest {
  @Test
  fun `create a projects task command`() {
    // given
    val projectRoot = File("~/MyProjects/Signal-Android")
    val command = GradleProjectsCommand.from(projectRoot)

    // when & then
    assertThat(command.text)
      .isEqualTo("~/MyProjects/Signal-Android/gradlew -q projects")
  }
}
