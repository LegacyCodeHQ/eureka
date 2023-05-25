import com.legacycode.ureka.gradle.ProjectStructure
import com.legacycode.ureka.gradle.commands.GradleDependenciesCommand
import com.legacycode.ureka.gradle.commands.GradleProjectsCommand
import java.io.File

fun main() {
  val projectsCommand = GradleProjectsCommand(File("").absoluteFile)
  val projectsOutput = projectsCommand.execute()

  val projectStructure = ProjectStructure.from(projectsOutput)
  val gradleDependenciesCommands = GradleDependenciesCommand.from(projectStructure)

  gradleDependenciesCommands.onEach {
    println(it.text)
  }
}
