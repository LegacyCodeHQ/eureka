package com.legacycode.ureka.gradle

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ProjectStructureTest {
  @Test
  fun `parse project structure`() {
    // given
    val projectsTaskOutput = TaskOutputResource("projects.txt").content

    // when
    val projectStructure = ProjectStructure.fromCommandLineOutput(projectsTaskOutput)

    // then
    Approvals.verify(projectStructure.printableProjectStructure)
  }
}
