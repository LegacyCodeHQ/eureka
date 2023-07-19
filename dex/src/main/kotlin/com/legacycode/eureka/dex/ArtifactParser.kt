package com.legacycode.eureka.dex

import java.io.File
import java.util.Locale

private const val APK_EXTENSION = "apk"

interface ArtifactParser {
  val file: File

  fun inheritanceAdjacencyList(): InheritanceAdjacencyList

  companion object {
    fun from(file: File): ArtifactParser {
      return if (file.extension.lowercase(Locale.ENGLISH) == APK_EXTENSION) {
        ClassInheritanceApkParser(file)
      } else {
        ClassInheritanceJarParser(file)
      }
    }
  }
}
