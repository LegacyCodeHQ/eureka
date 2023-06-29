package com.legacycode.eureka.cli.top

import com.jakewharton.picnic.table
import com.legacycode.eureka.vcs.ListFilesGitCommand
import java.io.File
import java.nio.file.Path
import java.util.Locale
import picocli.CommandLine.Command

@Command(
  name = "top",
  description = ["lists the largest Kotlin and Java source files"],
)
class TopCommand : Runnable {
  private val desiredExtensions = listOf("java", "kt")

  override fun run() {
    val pwd = Path.of("").toAbsolutePath()
    val filePaths = ListFilesGitCommand(pwd.toFile()).execute()
    val sourceFiles = filePaths.map(::File).countLines()
    printCommandOutput(sourceFiles)
  }

  private fun printCommandOutput(sourceFiles: List<LineCount>) {
    val filesTable = table {
      sourceFiles.forEachIndexed { index, (file, lines) ->
        row("${fileRank(index, sourceFiles.size)}. $file", lines)
      }
    }
    println(filesTable)
  }

  private fun fileRank(index: Int, totalFiles: Int): String {
    val length = totalFiles.toString().length
    return (index + 1).toString().padStart(length, ' ')
  }

  private fun List<File>.countLines(): List<LineCount> {
    return this
      .filter(::isDesiredFile)
      .map(LineCount::from)
      .sortedByDescending(LineCount::lines)
  }

  private fun isDesiredFile(file: File): Boolean {
    return file.extension.lowercase(Locale.ENGLISH) in desiredExtensions
  }
}
