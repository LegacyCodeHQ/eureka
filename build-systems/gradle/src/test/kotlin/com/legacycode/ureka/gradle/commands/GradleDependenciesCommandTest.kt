package com.legacycode.ureka.gradle.commands

import com.google.common.truth.Truth.assertThat
import com.legacycode.eureka.gradle.Project
import com.legacycode.eureka.gradle.ProjectStructure
import com.legacycode.eureka.gradle.commands.GradleDependenciesCommand
import com.legacycode.ureka.gradle.testing.TaskOutputResource
import java.io.File
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class GradleDependenciesCommandTest {
  @Test
  fun `create command from subproject`() {
    // given
    val project = Project("contacts")

    // when
    val command = GradleDependenciesCommand.from(File("/project/root"), project)

    // then
    assertThat(command.text)
      .isEqualTo("./gradlew -p /project/root -q contacts:dependencies")
  }

  @Test
  fun `create multiple commands from project structure`() {
    // given
    val projectsTaskOutput = TaskOutputResource.projects("Signal.txt").output
    val projectStructure = ProjectStructure.from(projectsTaskOutput)

    // when
    val commands = GradleDependenciesCommand.from(File("/project/root"), projectStructure)

    // then
    val listOfCommands = commands.joinToString("\n") { it.text }
    Approvals.verify(listOfCommands)
  }
}
