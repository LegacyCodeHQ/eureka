package com.legacycode.ureka.gradle

import java.io.File

class GradleProjectsCommand(private val projectRoot: File) {
  companion object {
    fun from(projectRoot: File): GradleProjectsCommand {
      return GradleProjectsCommand(projectRoot)
    }
  }

  val text: String
    get() = "${projectRoot.resolve("gradlew")} -q projects"
}
