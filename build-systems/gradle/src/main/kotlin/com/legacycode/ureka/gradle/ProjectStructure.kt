package com.legacycode.ureka.gradle

class ProjectStructure(
  private val rootProject: Project,
  private val subprojects: List<Project>,
) {
  companion object {
    fun fromCommandLineOutput(output: String): ProjectStructure {
      val lines = output.lines()
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

  val printableProjectStructure: String
    get() {
      val projectStructureBuilder = StringBuilder()

      projectStructureBuilder.appendLine("Root project '${rootProject.name}'")
      subprojects.forEachIndexed { index, project ->
        val indent = getIndent(index)

        projectStructureBuilder.appendLine("${indent}Project ':${project.name}'")
      }

      return projectStructureBuilder.toString()
    }

  private fun getIndent(index: Int): String {
    return if (index == subprojects.lastIndex) {
      "\\--- "
    } else {
      "+--- "
    }
  }
}
