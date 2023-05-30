package com.legacycode.eureka.gradle

import com.legacycode.eureka.gradle.commands.GradleDependenciesCommand
import com.legacycode.eureka.gradle.commands.GradleProjectsCommand
import java.io.File

fun runGradleCommands(projectRoot: File): String {
  val cmdFile = projectRoot.resolve("gradlew")
  val projectsCommand = GradleProjectsCommand(projectRoot)
  val projectsOutput = projectsCommand.execute()

  val projectStructure = ProjectStructure.from(CommandOutput(projectsOutput))
  val gradleDependenciesCommands = GradleDependenciesCommand.from(projectRoot, projectStructure)

  val resolvedDependencies = resolveDependencies(cmdFile, gradleDependenciesCommands)

  return plantUmlOutput(resolvedDependencies)
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

private fun plantUmlOutput(
  resolvedDependencies: Map<Project, List<SubprojectDependency>>,
): String {
  val umlBuilder = StringBuilder()

  umlBuilder.appendLine("@startuml")
  umlBuilder.appendLine("skinparam showEmptyMembers true")
  umlBuilder.appendLine()
  resolvedDependencies.entries.onEach { (project, dependencies) ->
    dependencies.forEach { dependency ->
      umlBuilder.appendLine("[${project.name}] ..> [${dependency.name}]")
    }
  }
  umlBuilder.appendLine()
  umlBuilder.appendLine("'workaround: show components without in/out dependencies")
  resolvedDependencies.entries.filter { it.value.isEmpty() }.onEach { (project, _) ->
    umlBuilder.appendLine("[${project.name}] --[hidden]-> [${project.name}]")
  }
  umlBuilder.appendLine("'end workaround")
  umlBuilder.appendLine()
  umlBuilder.appendLine("@enduml")

  return umlBuilder.toString()
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
