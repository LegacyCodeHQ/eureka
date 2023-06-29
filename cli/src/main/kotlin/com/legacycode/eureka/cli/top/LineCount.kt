package com.legacycode.eureka.cli.top

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import org.slf4j.LoggerFactory

data class LineCount private constructor(val file: File, val lines: Int) {
  companion object {
    private val logger = LoggerFactory.getLogger(LineCount::class.java)

    fun from(file: File): LineCount {
      val lines = countLines(file)
      return LineCount(file, lines)
    }

    private fun countLines(file: File): Int {
      var lineCount = 0

      try {
        BufferedReader(FileReader(file)).use { reader ->
          while (reader.readLine() != null) {
            lineCount++
          }
        }
      } catch (e: IOException) {
        logger.error("Counting lines for {} failed.", file.absolutePath, e)
      }

      return lineCount
    }
  }
}
