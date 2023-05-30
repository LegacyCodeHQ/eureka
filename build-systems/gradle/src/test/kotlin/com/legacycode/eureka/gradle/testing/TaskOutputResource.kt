package com.legacycode.eureka.gradle.testing

import com.legacycode.eureka.gradle.CommandOutput

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
      val resourcePath = "$taskName-outputs/$filename"
      val content = TaskOutputResource::class.java
        .classLoader
        .getResource(resourcePath)
        ?.readText()
      return CommandOutput(content!!)
    }
}
