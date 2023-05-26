import com.legacycode.ureka.gradle.CommandOutput
import com.legacycode.ureka.gradle.Project
import com.legacycode.ureka.gradle.ProjectStructure
import com.legacycode.ureka.gradle.SubprojectDependency
import com.legacycode.ureka.gradle.commands.GradleDependenciesCommand
import com.legacycode.ureka.gradle.commands.GradleProjectsCommand
import java.io.File

fun main() {
  val projectRoot = File("").absoluteFile
  val cmdFile = projectRoot.resolve("gradlew")
  val projectsCommand = GradleProjectsCommand(projectRoot)
  val projectsOutput = projectsCommand.execute()

  val projectStructure = ProjectStructure.from(CommandOutput(projectsOutput))
  val gradleDependenciesCommands = GradleDependenciesCommand.from(projectStructure)

  val resolvedDependencies = resolveDependencies(cmdFile, gradleDependenciesCommands)

  resolvedDependencies.entries.onEach { (project, dependencies) ->
    println("${project.name} (${dependencies.size})")

    dependencies.forEach {
      println("  → ${it.name}")
    }
    println()
  }
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
    resolvedDependencies[project] = dependencies
  }
  return resolvedDependencies.toMap()
}
