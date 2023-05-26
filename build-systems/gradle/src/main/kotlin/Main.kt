import com.legacycode.ureka.gradle.CommandOutput
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

  gradleDependenciesCommands.onEach { dependenciesCommand ->
    println(dependenciesCommand.project.name)
    val output = CommandOutput(dependenciesCommand.execute(cmdFile))
    val dependencies = SubprojectDependency.from(output)
    dependencies.forEach {
      println("  → ${it.name}")
    }
    println()
  }
}
