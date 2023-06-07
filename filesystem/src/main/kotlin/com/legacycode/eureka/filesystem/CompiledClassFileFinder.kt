package com.legacycode.eureka.filesystem

import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Path

class CompiledClassFileFinder {
  companion object {
    fun find(
      className: String,
      searchDirectory: String,
    ): Path? {
      // Prepare a list of all paths that partially match the query
      val potentialClassFilePaths = FileSystems.getDefault()
        .getPath(searchDirectory)
        .toFile()
        .walkTopDown()
        .filter { file -> file.extension == "class" }
        .filter { file -> isPotentialClassFilePath(className, file) }
        .toList()

      return if (potentialClassFilePaths.size == 1) {
        // If there is only one result it means we found the exact class
        potentialClassFilePaths.first().toPath()
      } else if (potentialClassFilePaths.size > 1) {
        // If there is more than one result it means we need to find the exact class
        potentialClassFilePaths.find {
          val partialClassNamePath = className.replace(".", "/")
          it.absolutePath.endsWith("/$partialClassNamePath.class")
        }?.toPath()
      } else {
        null
      }
    }

    private fun isPotentialClassFilePath(className: String, file: File): Boolean {
      val partialClassNamePath = className.replace(".", "/")
      return file.isFile && !file.absolutePath.contains("incrementalData") &&
        !file.absolutePath.contains("asm_instrumented_project_classes") &&
        !file.absolutePath.contains("jacoco_instrumented_classes") &&
        file.absolutePath.endsWith("$partialClassNamePath.class")
    }
  }
}
