package com.legacycode.ureka.gradle.commands

import com.legacycode.ureka.gradle.Project
import com.legacycode.ureka.gradle.ProjectStructure
import java.io.File

class GradleDependenciesCommand(
  private val root: File,
  val project: Project,
) : Command {
  companion object {
    fun from(root: File, project: Project): GradleDependenciesCommand {
      return GradleDependenciesCommand(root, project)
    }

    fun from(root: File, projectStructure: ProjectStructure): List<GradleDependenciesCommand> {
      return projectStructure.subprojects.map { from(root, it) }
    }
  }

  override val text: String
    get() = "./gradlew -p ${root.absolutePath} -q ${project.name}:dependencies"
}
