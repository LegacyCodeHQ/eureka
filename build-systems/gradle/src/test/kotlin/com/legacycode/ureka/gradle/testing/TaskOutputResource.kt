package com.legacycode.ureka.gradle.testing

import com.legacycode.ureka.gradle.CommandOutput

class TaskOutputResource private constructor(
  private val taskName: String,
  private val filename: String,
) {
  companion object {
    fun projects(filename: String): TaskOutputResource {
      return TaskOutputResource("projects", filename)
    }

    fun dependencies(filename: String): TaskOutputResource {
      return TaskOutputResource("dependencies", filename)
    }
  }

  val output: CommandOutput
    get() {
      val content = TaskOutputResource::class.java
        .classLoader
        .getResource("$taskName-outputs/$filename")!!
        .readText()
      return CommandOutput(content)
    }
}
