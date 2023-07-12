package com.legacycode.eureka.dex

import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.Opcodes

class ApkParser(private val apkFile: File) {
  companion object {
    private const val DEX_FILE_EXTENSION = ".dex"
    private const val KITKAT = 19
  }

  fun inheritanceAdjacencyList(): InheritanceAdjacencyList {
    val adjacencyList = InheritanceAdjacencyList()

    ZipFile(apkFile).use { zipFile ->
      val dexFileEntries = zipFile.entries().asSequence().filter(::dexFileFilter)
      for (dexFileEntry in dexFileEntries) {
        val dexEntry = DexFileFactory.loadDexEntry(
          apkFile,
          dexFileEntry.name,
          true,
          Opcodes.forApi(KITKAT),
        )

        for (classDef in dexEntry.dexFile.classes) {
          val classType = classDef.type
          val superclassType = classDef.superclass

          adjacencyList.add(Ancestor(superclassType!!), Child(classType))
        }
      }
    }

    return adjacencyList
  }

  private fun dexFileFilter(entry: ZipEntry): Boolean {
    return entry.name.endsWith(DEX_FILE_EXTENSION)
  }
}
