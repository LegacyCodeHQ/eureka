import com.github.gradle.node.npm.task.NpmTask

plugins {
  id("com.github.node-gradle.node") version "3.1.0"
}

node {
  download.set(true)
  version.set("18.12.0")
}

registerNpmTask("testJs", "test")
registerNpmTask("eslintCheck", "run", "eslintCheck")
registerNpmTask("eslintFix", "run", "eslintFix")

fun registerNpmTask(name: String, command: String, vararg commandArgs: String) {
  tasks.register(name, NpmTask::class) {
    dependsOn(tasks.findByName("npmInstall"))
    args.set(listOf(command, *commandArgs))
  }
}
