package com.legacycode.ureka.gradle

class ProjectStructure(private val rootProject: Project) {
  companion object {
    fun fromCommandLineOutput(output: String): ProjectStructure {
      val lines = output.lines()
      val rootProjectLine = lines.firstOrNull { it.startsWith("Root project '") }
      val rootProjectName = rootProjectLine?.substringAfter("Root project '")?.substringBefore("'")
      val rootProject = Project(rootProjectName ?: "Signal")
      val projectStructure = ProjectStructure(rootProject)

      for (line in lines) {
        if (line.startsWith("+--- Project") || line.startsWith("\\--- Project")) {
          val path = line.substring(line.indexOf(':') + 1).trim()
          projectStructure.addProject(path)
        }
      }

      return projectStructure
    }
  }

  private val projects: MutableMap<String, Project> = mutableMapOf()

  fun addProject(name: String) {
    val project = Project(name.substring(0, name.length - 1))
    projects[name] = project

    val parentPath = getParentPath(name)
    if (parentPath != null) {
      val parentProject = projects[parentPath]
      parentProject?.subprojects?.add(project)
      project.parentProject = parentProject
    } else {
      rootProject.subprojects.add(project)
      project.parentProject = rootProject
    }
  }

  val printableProjectStructure: String
    get() {
      val projectStructureBuilder = StringBuilder()
      buildPrintableProjectStructure(rootProject, "", "", projectStructureBuilder)
      return projectStructureBuilder.toString()
    }

  private fun getParentPath(path: String): String? {
    val separatorIndex = path.lastIndexOf(':')
    return if (separatorIndex != -1) path.substring(0, separatorIndex) else null
  }

  private fun buildPrintableProjectStructure(
    project: Project,
    indent: String,
    branchIndent: String,
    projectStructureBuilder: StringBuilder,
  ) {
    if (project == rootProject) {
      projectStructureBuilder.appendLine("Root project '${project.name}'")
    } else {
      projectStructureBuilder.appendLine("${indent}Project ':${project.name}'")
    }
    val subProjects = project.subprojects
    for (i in subProjects.indices) {
      val subProject = subProjects[i]
      val nextBranchIndent = if (i == subProjects.lastIndex) "$branchIndent   " else "$branchIndent|  "
      if (i == subProjects.lastIndex) {
        buildPrintableProjectStructure(subProject, "$branchIndent\\--- ", nextBranchIndent, projectStructureBuilder)
      } else {
        buildPrintableProjectStructure(subProject, "$branchIndent+--- ", nextBranchIndent, projectStructureBuilder)
      }
    }
  }
}
