package com.legacycode.eureka.dex

import java.io.File
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.DexBackedClassDef

class ClassInheritanceApkParser(override val file: File) : ArtifactParser {
  companion object {
    private const val DEX_FILE_EXTENSION = ".dex"
    private const val KITKAT = 19

    private const val REGEX_ANONYMOUS_INNER_CLASS_SUFFIX = ".+\\$\\d+;$"
  }

  override fun inheritanceAdjacencyList(): InheritanceAdjacencyList {
    return ZipFile(file).use(::buildInheritanceAdjacencyList)
  }

  private fun buildInheritanceAdjacencyList(zipFile: ZipFile): InheritanceAdjacencyList {
    val dexFileEntries = zipFile.entries().asSequence().filter(::isDexFile)
    val adjacencyList = InheritanceAdjacencyList()

    for (dexFileEntry in dexFileEntries) {
      for (classDef in dexFileEntry.dexClasses) {
        if (isNamedClass(classDef)) {
          val classType = classDef.type
          val superclassType = classDef.superclass
          adjacencyList.add(Ancestor(superclassType!!), Child(classType))
        }
      }
    }

    return adjacencyList
  }

  private val ZipEntry.dexClasses: MutableSet<out DexBackedClassDef>
    get() {
      return DexFileFactory
        .loadDexEntry(file, name, true, Opcodes.forApi(KITKAT))
        .dexFile
        .classes
    }

  private fun isDexFile(entry: ZipEntry): Boolean =
    entry.name.endsWith(DEX_FILE_EXTENSION)

  private fun isNamedClass(classDef: DexBackedClassDef): Boolean =
    !isAnonymousInnerClass(classDef.type) && !isSyntheticClass(classDef)

  private fun isAnonymousInnerClass(classType: String): Boolean =
    Pattern.matches(REGEX_ANONYMOUS_INNER_CLASS_SUFFIX, classType)

  private fun isSyntheticClass(classDef: DexBackedClassDef): Boolean =
    classDef.accessFlags and AccessFlags.SYNTHETIC.value != 0
}
