package com.legacycode.eureka.cli.top

import com.jakewharton.picnic.table
import com.legacycode.eureka.vcs.ListFilesGitCommand
import java.io.File
import java.nio.file.Path
import java.util.Locale
import kotlin.io.path.exists
import kotlin.io.path.name
import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
  name = "top",
  description = ["lists the largest Kotlin and Java source files"],
)
class TopCommand : Runnable {
  private val desiredExtensions = listOf("java", "kt")

  @Option(
    names = ["-c", "--count"],
    description = ["number of files to list"]
  )
  var count: Int? = null

  override fun run() {
    val pwd = Path.of("").toAbsolutePath()
    if (!pwd.isGitRepo) {
      println("Uh oh! not a git repo. Run the command inside a git directory.")
      return
    }
    val filePaths = ListFilesGitCommand(pwd.toFile()).execute()
    val sourceFiles = filePaths.map(::File).countLines()
    if (count != null) {
      return printCommandOutput(sourceFiles.take(count!!), sourceFiles.size)
    }
    printCommandOutput(sourceFiles)
  }

  private fun printCommandOutput(
    sourceFiles: List<LineCount>,
    actualCount: Int = sourceFiles.size,
  ) {
    val showingFewerFilesThanDiscovered = actualCount > sourceFiles.size
    if (showingFewerFilesThanDiscovered) {
      val message = "Showing ${sourceFiles.size} of $actualCount"
      println(message)
      repeat(message.length) { print("-") }
      println()
    }
    val filesTable = table {
      sourceFiles.forEachIndexed { index, (file, lines) ->
        row("${fileRank(index, sourceFiles.size)}. $file", "  $lines")
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

private val Path.isGitRepo: Boolean
  get() {
    return name == ".git" || resolve(".git").exists()
  }
