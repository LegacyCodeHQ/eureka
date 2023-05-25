import com.legacycode.ureka.gradle.ProjectStructure
import com.legacycode.ureka.gradle.commands.GradleProjectsCommand
import java.io.File
import org.buildobjects.process.ProcBuilder

fun main() {
  val projectsCommand = GradleProjectsCommand(File("").absoluteFile)
  val split = projectsCommand.text.split(" ")
  val cmd = split.first()
  val args = split.drop(1).toTypedArray()
  val projectsOutput = ProcBuilder.run(cmd, *args)

  val projectStructure = ProjectStructure.from(projectsOutput)

  println(projectStructure)
}
