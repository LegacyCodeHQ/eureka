package com.legacycode.eureka.dex.dependency

import com.legacycode.eureka.dex.AdjacencyList
import com.legacycode.eureka.dex.Ancestor
import com.legacycode.eureka.dex.Child
import com.legacycode.eureka.dex.inheritance.InheritanceApkParser
import java.io.File
import java.util.regex.Pattern
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.DexBackedClassDef
import org.jf.dexlib2.dexbacked.DexBackedMethod
import org.jf.dexlib2.iface.instruction.ReferenceInstruction
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.reference.TypeReference

class DependencyApkParser(private val file: File) {
  companion object {
    private const val DEX_FILE_EXTENSION = ".dex"
    private const val KITKAT = 19

    private const val REGEX_ANONYMOUS_INNER_CLASS_SUFFIX = ".+\\$\\d+;$"
  }

  fun buildDependencyGraph(): AdjacencyList {
    val inheritanceTree = InheritanceApkParser(file).buildInheritanceTree()
    val classInheritanceTree = inheritanceTree.prune(Ancestor("Landroidx/fragment/app/Fragment;"))

    return ZipFile(file).use {
      buildDependencyGraph(it, classInheritanceTree)
    }
  }

  private fun buildDependencyGraph(
    zipFile: ZipFile,
    inheritanceTree: AdjacencyList,
  ): AdjacencyList {
    val dexFileEntries = zipFile.entries().asSequence().filter(::isDexFile)
    val adjacencyList = AdjacencyList()

    for (dexFileEntry in dexFileEntries) {
      for (classDef in dexFileEntry.dexClasses) {
        val classType = classDef.type
        val isClassInInheritanceTree = isNamedClass(classDef) && inheritanceTree.contains(classType)
        if (isClassInInheritanceTree) {
          val allDependencies = classDef.findDependencies()
          val dependenciesInInheritanceTree = allDependencies.filter(inheritanceTree::contains)

          for (dependencyType in dependenciesInInheritanceTree) {
            if (dependencyType !in inheritanceTree.ancestors().map(Ancestor::id)) {
              adjacencyList.add(Ancestor(dependencyType), Child(classType))
            }
          }
        }
      }
    }

    return adjacencyList
  }

  private fun isDexFile(entry: ZipEntry): Boolean =
    entry.name.endsWith(DEX_FILE_EXTENSION)

  private val ZipEntry.dexClasses: MutableSet<out DexBackedClassDef>
    get() {
      return DexFileFactory
        .loadDexEntry(file, name, true, Opcodes.forApi(KITKAT))
        .dexFile
        .classes
    }

  private fun isNamedClass(classDef: DexBackedClassDef): Boolean =
    !isAnonymousInnerClass(classDef.type) && !isSyntheticClass(classDef)

  private fun isAnonymousInnerClass(classType: String): Boolean =
    Pattern.matches(REGEX_ANONYMOUS_INNER_CLASS_SUFFIX, classType)

  private fun isSyntheticClass(classDef: DexBackedClassDef): Boolean =
    classDef.accessFlags and AccessFlags.SYNTHETIC.value != 0

  private fun DexBackedClassDef.findDependencies(): Set<String> {
    val dependencies = mutableSetOf<String>()

    addFieldTypes(dependencies)
    addTypesFromMethods(dependencies)

    dependencies.remove(this.type)
    dependencies.remove(this.superclass)

    return dependencies.toSet()
  }

  private fun DexBackedClassDef.addFieldTypes(outDependencies: MutableSet<String>) {
    for (field in fields) {
      outDependencies.add(field.type)
    }
  }

  private fun DexBackedClassDef.addTypesFromMethods(outDependencies: MutableSet<String>) {
    for (method in methods) {
      outDependencies.add(method.returnType)
      method.addParameterTypes(outDependencies)
      method.addTypesFromInstructions(outDependencies)
    }
  }

  private fun DexBackedMethod.addParameterTypes(
    outDependencies: MutableSet<String>,
  ) {
    for (parameterType in parameterTypes) {
      outDependencies.add(parameterType)
    }
  }

  private fun DexBackedMethod.addTypesFromInstructions(
    outDependencies: MutableSet<String>,
  ) {
    implementation?.instructions?.forEach { instruction ->
      if (instruction is ReferenceInstruction) {
        when (val reference = instruction.reference) {
          is TypeReference -> outDependencies.add(reference.type)
          is FieldReference -> {
            outDependencies.add(reference.definingClass)
            outDependencies.add(reference.type)
          }

          is MethodReference -> {
            outDependencies.add(reference.definingClass)
            outDependencies.add(reference.returnType)
            reference.parameterTypes.forEach { parameterType ->
              outDependencies.add(parameterType.toString())
            }
          }
        }
      }
    }
  }
}
