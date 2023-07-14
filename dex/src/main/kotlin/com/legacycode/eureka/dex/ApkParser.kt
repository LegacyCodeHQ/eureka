package com.legacycode.eureka.dex

import java.io.File
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.DexBackedClassDef

class ApkParser(override val file: File) : ArtifactParser {
  companion object {
    private const val DEX_FILE_EXTENSION = ".dex"
    private const val KITKAT = 19

    private const val REGEX_ANONYMOUS_INNER_CLASS_SUFFIX = ".+\\$\\d+;$"
  }

  override fun inheritanceAdjacencyList(): InheritanceAdjacencyList {
    val adjacencyList = InheritanceAdjacencyList()

    ZipFile(file).use { zipFile ->
      parseDexClassFiles(zipFile, adjacencyList)
    }

    return adjacencyList
  }

  private fun parseDexClassFiles(
    zipFile: ZipFile,
    outAdjacencyList: InheritanceAdjacencyList,
  ) {
    val dexFileEntries = zipFile.entries().asSequence().filter(::dexFileFilter)
    for (dexFileEntry in dexFileEntries) {
      val dexEntry = DexFileFactory.loadDexEntry(
        file,
        dexFileEntry.name,
        true,
        Opcodes.forApi(KITKAT),
      )

      for (classDef in dexEntry.dexFile.classes) {
        val classType = classDef.type
        val superclassType = classDef.superclass

        if (!isAnonymousInnerClass(classType) && !isSyntheticClass(classDef)) {
          outAdjacencyList.add(Ancestor(superclassType!!), Child(classType))
        }
      }
    }
  }

  private fun isAnonymousInnerClass(classType: String): Boolean {
    return Pattern.matches(REGEX_ANONYMOUS_INNER_CLASS_SUFFIX, classType)
  }

  private fun isSyntheticClass(classDef: DexBackedClassDef): Boolean {
    return classDef.accessFlags and AccessFlags.SYNTHETIC.value != 0
  }

  private fun dexFileFilter(entry: ZipEntry): Boolean {
    return entry.name.endsWith(DEX_FILE_EXTENSION)
  }
}
