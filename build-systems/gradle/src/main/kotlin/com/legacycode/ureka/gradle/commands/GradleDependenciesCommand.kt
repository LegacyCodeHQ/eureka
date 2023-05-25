package com.legacycode.ureka.gradle.commands

import com.legacycode.ureka.gradle.Project
import com.legacycode.ureka.gradle.ProjectStructure

class GradleDependenciesCommand(
  private val project: Project,
) {
  companion object {
    fun from(project: Project): GradleDependenciesCommand {
      return GradleDependenciesCommand(project)
    }

    fun from(projectStructure: ProjectStructure): List<GradleDependenciesCommand> {
      return projectStructure.subprojects.map(Companion::from)
    }
  }

  val text: String
    get() = "./gradlew -q ${project.name}:dependencies"
}
