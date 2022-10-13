package io.redgreen.tumbleweed.filesystem

import java.nio.file.FileSystems
import java.nio.file.Path

class CompiledClassFileFinder {
  companion object {
    fun find(
      className: String,
      searchDirectory: String,
    ): Path? {
      val classFilePath = FileSystems.getDefault()
        .getPath(searchDirectory)
        .toFile()
        .walkTopDown()
        .filter { file -> file.extension == "class" }
        .find {
          val partialClassNamePath = className.replace(".", "/")
          it.isFile && !it.absolutePath.contains("incrementalData") &&
            it.absolutePath.endsWith("$partialClassNamePath.class")
        }

      return classFilePath?.toPath()
    }
  }
}
