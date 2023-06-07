package com.legacycode.eureka.cli.dev.methods

import com.legacycode.eureka.ClassScanner
import com.legacycode.eureka.ClassStructure
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Command(
  name = "methods",
  description = ["(dev) creates a method list file for a class file"],
  hidden = true,
)
class MethodsCommand : Runnable {
  @Parameters(
    index = "0",
    description = ["path to the compiled class file"],
    arity = "1",
  )
  lateinit var compiledClassFile: File

  private val skipList = listOf(
    "void <init>()",
    "void <clinit>()",
  ) /* should skip java.lang.Object methods, etc., */

  override fun run() {
    val classStructure = ClassScanner.scan(compiledClassFile)

    val methodList = classStructure.createMethodList(skipList)

    val classFileDirectory = compiledClassFile.parentFile
    val fullyQualifiedClassName = classStructure.type.name.replace("/", ".")
    val methodListOutputFile = classFileDirectory.resolve("$fullyQualifiedClassName.methods")

    methodListOutputFile.writeText(methodList)

    println("Method list written to: $methodListOutputFile")
  }
}

fun ClassStructure.createMethodList(skipList: List<String>): String {
  return methods
    .map { it.signature.verbose }
    .filter { it !in skipList }
    .joinToString("\n")
}
