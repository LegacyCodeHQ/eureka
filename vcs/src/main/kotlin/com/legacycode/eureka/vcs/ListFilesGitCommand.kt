package com.legacycode.eureka.vcs

import java.io.File
import org.buildobjects.process.ProcBuilder

class ListFilesGitCommand(private val repo: File) {
  fun execute(): List<String> {
    val args = "--git-dir=${repo.canonicalPath}${File.separatorChar}.git ls-files"
      .split(" ")
      .toTypedArray()

    return ProcBuilder("git")
      .withNoTimeout()
      .withArgs(*args)
      .run()!!
      .outputString
      .lines()
      .filter { it.isNotBlank() }
  }
}
