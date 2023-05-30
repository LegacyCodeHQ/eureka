package com.legacycode.eureka.gradle

import com.legacycode.eureka.gradle.testing.TaskOutputResource
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class ProjectStructureTest {
  @Test
  fun `parse project structure`() {
    // given
    val projectsTaskOutput = TaskOutputResource.projects("Signal.txt").output

    // when
    val projectStructure = ProjectStructure.from(projectsTaskOutput)

    // then
    Approvals.verify(projectStructure.printable)
  }
}
