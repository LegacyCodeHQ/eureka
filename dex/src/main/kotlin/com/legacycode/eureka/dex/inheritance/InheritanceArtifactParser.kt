package com.legacycode.eureka.dex.inheritance

import com.legacycode.eureka.dex.AdjacencyList
import java.io.File
import java.util.Locale

private const val APK_EXTENSION = "apk"

interface InheritanceArtifactParser {
  val file: File

  fun buildAdjacencyList(): AdjacencyList

  companion object {
    fun from(file: File): InheritanceArtifactParser {
      return if (file.isApk) {
        InheritanceApkParser(file)
      } else {
        InheritanceJarParser(file)
      }
    }

    private val File.isApk: Boolean
      get() {
        return extension.lowercase(Locale.ENGLISH) == APK_EXTENSION
      }
  }
}
