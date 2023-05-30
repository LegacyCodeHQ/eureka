package com.legacycode.eureka.gradle

data class ProjectStructure(
  val rootProject: Project,
  val subprojects: List<Project>,
) {
  companion object {
    fun from(output: CommandOutput): ProjectStructure {
      val lines = output.content.lines()
      val rootProject = parseRootProject(lines)

      val subprojects = mutableListOf<Project>()
      for (line in lines) {
        if (isProjectLine(line)) {
          subprojects.add(parseSubproject(line))
        }
      }

      return ProjectStructure(rootProject, subprojects.toList())
    }

    private fun parseRootProject(lines: List<String>): Project {
      val rootProjectLine = lines.firstOrNull { it.startsWith("Root project '") }
      val rootProjectName = rootProjectLine?.substringAfter("Root project '")?.substringBefore("'")!!
      return Project(rootProjectName)
    }

    private fun isProjectLine(line: String): Boolean {
      return line.startsWith("+--- Project") || line.startsWith("\\--- Project")
    }

    private fun parseSubproject(line: String): Project {
      val path = line.substring(line.indexOf(':') + 1).trim()
      return Project(path.substring(0, path.length - 1))
    }
  }
}
