package com.legacycode.ureka.gradle

class TaskOutputResource(private val filename: String) {
  val content: String
    get() {
      return TaskOutputResource::class.java
        .classLoader
        .getResource("task-outputs/$filename")!!
        .readText()
    }
}
