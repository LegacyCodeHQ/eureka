package com.legacycode.eureka.gradle

import com.legacycode.eureka.gradle.commands.GradleDependenciesCommand
import com.legacycode.eureka.gradle.commands.GradleProjectsCommand
import com.legacycode.eureka.gradle.metrics.Instability
import com.legacycode.eureka.gradle.metrics.Instability.Companion.MAXIMALLY_STABLE
import com.legacycode.eureka.gradle.metrics.Instability.Companion.MAXIMALLY_UNSTABLE
import com.legacycode.eureka.gradle.metrics.Instability.Companion.UNUSED
import java.io.File

private const val RADIX_HEX = 16
private const val CHANNEL_MAX = 255

fun runGradleCommands(projectRoot: File): String {
  val cmdFile = projectRoot.resolve("gradlew")
  val projectsCommand = GradleProjectsCommand(projectRoot)
  val projectsOutput = projectsCommand.execute()

  val projectStructure = ProjectStructure.from(CommandOutput(projectsOutput))
  val gradleDependenciesCommands = GradleDependenciesCommand.from(projectRoot, projectStructure)

  val resolvedDependencies = resolveDependencies(cmdFile, gradleDependenciesCommands)

  return graphvizOutput(projectStructure, resolvedDependencies)
}

private fun resolveDependencies(
  cmdFile: File,
  commands: List<GradleDependenciesCommand>,
): Map<Project, List<SubprojectDependency>> {
  val resolvedDependencies = mutableMapOf<Project, List<SubprojectDependency>>()
  commands.onEach { dependenciesCommand ->
    val project = dependenciesCommand.project
    val output = CommandOutput(dependenciesCommand.execute(cmdFile))
    val dependencies = SubprojectDependency.from(output)
    resolvedDependencies[project] = dependencies.filter { it.name != project.name }
  }
  return resolvedDependencies.toMap()
}

private fun printDebugOutput(resolvedDependencies: Map<Project, List<SubprojectDependency>>) {
  resolvedDependencies.entries.onEach { (project, dependencies) ->
    println("${project.name} (${dependencies.size})")

    dependencies.forEach {
      println("  → ${it.name}")
    }
    println()
  }
}

fun graphvizOutput(
  projectStructure: ProjectStructure,
  subprojectDependencies: Map<Project, List<SubprojectDependency>>,
): String {
  val graphvizBuilder = StringBuilder()

  graphvizBuilder.appendLine("""digraph "${projectStructure.rootProject.name}" {""")
  graphvizBuilder.appendLine("""  node [fontname="Arial"];""")
  graphvizBuilder.appendLine()

  projectStructure.subprojects.onEach { project ->
    graphvizBuilder.appendLine(createNode(project, subprojectDependencies))
  }

  graphvizBuilder.appendLine()
  val projectsWithSubprojectDependencies = subprojectDependencies.filter { it.value.isNotEmpty() }
  projectsWithSubprojectDependencies.onEach { (project, subprojectDependencies) ->
    subprojectDependencies.onEach { subprojectDependency ->
      graphvizBuilder.appendLine(mapProjectToDependency(project, subprojectDependency))
    }
  }

  graphvizBuilder.appendLine("}")

  return graphvizBuilder.toString()
}

private fun createNode(
  project: Project,
  dependencies: Map<Project, List<SubprojectDependency>>,
): String {
  val projectDependencies = dependencies[project]
  val fanOut = projectDependencies?.size ?: 0
  val fanIn = dependencies.flatMap { it.value }.count { it.name == project.name }
  val instability = Instability(fanOut, fanIn)

  val nodeText = """  "${project.name}" [label="${project.name}\n($fanOut, $fanIn, I=${instability.value})""""
  val nodeStyle = nodeStyle(instability)
  return nodeText + nodeStyle
}

private fun nodeStyle(instability: Instability): String {
  val color = when (instability.value) {
    MAXIMALLY_UNSTABLE -> "#01BFFFFF"
    MAXIMALLY_STABLE -> "#FF7F50FF"
    UNUSED -> "#C1CDCDFF"
    else -> {
      val alpha = ((1 - instability.value.toFloat()) * CHANNEL_MAX).toInt()
      "#FF7F50${alpha.toString(RADIX_HEX)}"
    }
  }

  return """, style="filled", fillcolor="$color"]"""
}

private fun mapProjectToDependency(
  project: Project,
  subprojectDependency: SubprojectDependency,
): String {
  return """  "${project.name}" -> "${subprojectDependency.name}""""
}
