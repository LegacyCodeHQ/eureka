package com.legacycode.ureka.gradle

class GradleDependenciesCommand(
  private val project: Project,
) {
  companion object {
    fun from(project: Project): GradleDependenciesCommand {
      return GradleDependenciesCommand(project)
    }

    fun from(projectStructure: ProjectStructure): List<GradleDependenciesCommand> {
      return projectStructure.subprojects.map(GradleDependenciesCommand::from)
    }
  }

  val text: String
    get() = "./gradlew -q ${project.name}:dependencies"
}
