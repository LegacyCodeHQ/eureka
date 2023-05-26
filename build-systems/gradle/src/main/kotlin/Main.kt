import com.legacycode.ureka.gradle.CommandOutput
import com.legacycode.ureka.gradle.ProjectStructure
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

  gradleDependenciesCommands.onEach {
    println(it.execute(cmdFile))
  }
}
