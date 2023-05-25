package com.legacycode.ureka.gradle

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class GradleDependenciesCommandTest {
  @Test
  fun `create command from subproject`() {
    // given
    val project = Project("contacts")

    // when
    val command = GradleDependenciesCommand.from(project)

    // then
    assertThat(command.text)
      .isEqualTo("./gradlew -q contacts:dependencies")
  }
}
