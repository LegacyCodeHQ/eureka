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
import picocli.CommandLine.Parameters

@Command(
  name = "top",
  description = ["lists the largest Kotlin and Java source files"],
)
class TopCommand : Runnable {
  private val desiredExtensions = listOf("java", "kt")

  @Parameters(
    index = "0",
    description = ["number of files to list"],
    arity = "1",
  )
  var count: Int? = null

  @Option(
    names = ["-r", "--repo"],
    description = ["path to the git repo"],
    required = false,
  )
  var repoDir: Path? = null

  @Option(
    names = ["-c", "--csv"],
    description = ["print output in CSV format"],
  )
  var csv: Boolean = false

  override fun run() {
    val projectRoot = repoDir ?: Path.of("").toAbsolutePath()
    if (!projectRoot.isGitRepo) {
      println("Uh oh! not a git repo. Run the command inside a git directory.")
      return
    }
    val filePaths = ListFilesGitCommand(projectRoot.toFile()).execute()
    val sourceFiles = filePaths.map { projectRoot.toFile().resolve(it) }.countLines()

    printCommandOutput(projectRoot, sourceFiles)
  }

  private fun printCommandOutput(projectRoot: Path, sourceFiles: List<LineCount>) {
    if (csv) {
      printCommandOutputCsv(projectRoot, sourceFiles)
    } else {
      if (count != null) {
        printFileLocTable(projectRoot, sourceFiles.take(count!!), sourceFiles.size)
      } else {
        printFileLocTable(projectRoot, sourceFiles)
      }
    }
  }

  private fun printCommandOutputCsv(projectRoot: Path, sourceFiles: List<LineCount>) {
    println("File,Lines")
    sourceFiles
      .take(count ?: sourceFiles.size)
      .forEach { lineCount -> println("${relativePathInsideRoot(projectRoot, lineCount.file)},${lineCount.lines}") }
  }

  private fun printFileLocTable(
    projectRoot: Path,
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
        row("${fileNumber(index, sourceFiles.size)}. ${relativePathInsideRoot(projectRoot, file)}", "  $lines")
      }
    }
    println(filesTable)
  }

  private fun fileNumber(index: Int, totalFiles: Int): String {
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

  private fun relativePathInsideRoot(projectRoot: Path, file: File): String {
    return file.toString().substringAfter(projectRoot.toString()).drop(1)
  }
}

private val Path.isGitRepo: Boolean
  get() {
    return name == ".git" || resolve(".git").exists()
  }
