package io.redgreen.tumbleweed.filesystem

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
        .filter { file ->
          val partialClassNamePath = className.replace(".", "/")
          file.isFile && !file.absolutePath.contains("incrementalData") &&
            file.absolutePath.endsWith("$partialClassNamePath.class")
        }
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
  }
}
