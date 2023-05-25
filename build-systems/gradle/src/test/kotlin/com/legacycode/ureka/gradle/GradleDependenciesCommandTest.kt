package com.legacycode.ureka.gradle

import com.google.common.truth.Truth.assertThat
import org.approvaltests.Approvals
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

  @Test
  fun `create multiple commands from project structure`() {
    // given
    val projectsTaskOutput = TaskOutputResource("projects.txt").content
    val projectStructure = ProjectStructure.from(projectsTaskOutput)

    // when
    val commands = GradleDependenciesCommand.from(projectStructure)

    // then
    val listOfCommands = commands.joinToString("\n") { it.text }
    Approvals.verify(listOfCommands)
  }
}
