package com.legacycode.ureka.gradle.commands

import com.google.common.truth.Truth.assertThat
import com.legacycode.eureka.gradle.commands.GradleProjectsCommand
import java.io.File
import org.junit.jupiter.api.Test

class GradleProjectsCommandTest {
  @Test
  fun `create a projects task command`() {
    // given
    val projectRoot = File("/projects/Signal-Android")
    val command = GradleProjectsCommand.from(projectRoot)

    // when & then
    assertThat(command.text)
      .isEqualTo("/projects/Signal-Android/gradlew -p /projects/Signal-Android -q projects")
  }
}
