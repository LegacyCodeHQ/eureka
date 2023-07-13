package com.legacycode.eureka.dex

import java.io.File
import java.util.zip.ZipInputStream
import net.bytebuddy.jar.asm.ClassReader

class JvmArtifactParser(override val file: File) : ArtifactParser {
  companion object {
    private const val CLASS_FILE_EXTENSION = ".class"
  }

  override fun inheritanceAdjacencyList(): InheritanceAdjacencyList {
    val adjacencyList = InheritanceAdjacencyList()

    file.inputStream().use { inputStream ->
      val zipInputStream = ZipInputStream(inputStream)
      var entry = zipInputStream.nextEntry
      while (entry != null) {
        if (!entry.isDirectory && entry.name.endsWith(CLASS_FILE_EXTENSION)) {
          val classBytes = zipInputStream.readBytes()
          val classReader = ClassReader(classBytes)
          println("${classReader.className} -> ${classReader.superName}")
          adjacencyList.add(Ancestor("L${classReader.superName};"), Child("L${classReader.className};"))
        }
        entry = zipInputStream.nextEntry
      }
    }

    return adjacencyList
  }
}
