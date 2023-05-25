package com.legacycode.ureka.gradle

class GradleDependenciesCommand(
  private val project: Project,
) {
  companion object {
    fun from(project: Project): GradleDependenciesCommand {
      return GradleDependenciesCommand(project)
    }
  }

  val text: String
    get() = "./gradlew -q ${project.name}:dependencies"
}
