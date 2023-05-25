package com.legacycode.ureka.gradle.commands

import java.io.File

class GradleProjectsCommand(private val projectRoot: File): Command {
  companion object {
    fun from(projectRoot: File): GradleProjectsCommand {
      return GradleProjectsCommand(projectRoot)
    }
  }

  override val text: String
    get() = "${projectRoot.resolve("gradlew")} -q projects"
}
