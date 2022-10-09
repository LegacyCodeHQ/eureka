package io.redgreen.tumbleweed.cli.commands

import io.redgreen.tumbleweed.ClassScanner
import io.redgreen.tumbleweed.ClassStructure
import io.redgreen.tumbleweed.Member
import io.redgreen.tumbleweed.filesystem.CompiledClassFileFinder
import io.redgreen.tumbleweed.web.observablehq.json
import java.io.File
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

@Command(
  name = "json",
  description = ["generates ObservableHQ JSON for the class (for debugging)"],
  hidden = true,
)
class JsonCommand : Runnable {
  @Parameters(
    index = "0",
    description = ["uniquely identifiable (partially or fully) qualified class name"],
    arity = "1",
  )
  lateinit var className: String

  @Option(
    names = ["-b", "--buildDir"],
    description = ["path to the build directory"],
    required = false,
  )
  var buildDir: File? = null

  @Option(
    names = ["-c", "--check"],
    description = ["verify the generated JSON for inconsistencies"],
    required = false,
  )
  var check: Boolean = false

  override fun run() {
    val classFilePath = CompiledClassFileFinder
      .find(className, (buildDir ?: File("")).absolutePath)
      ?: throw IllegalArgumentException("Class file not found for $className")

    val classStructure = ClassScanner.scan(classFilePath.toFile())
    if (check) {
      classStructure.check()
    } else {
      println(classStructure.json)
    }
  }

  private fun ClassStructure.check() {
    val membersInClassStructure = (fields + methods).toSet()
    val membersInRelationship = relationships.flatMap { listOf(it.source, it.target) }.toSet()
    val fqClassName = "$`package`.$className"

    if (membersInClassStructure != membersInRelationship) {
      val missingMembers = membersInRelationship - membersInClassStructure
      val membersWithoutRelationships = membersInClassStructure - membersInRelationship
      printReport(missingMembers, membersWithoutRelationships, fqClassName)
    } else {
      println("No inconsistencies found in '$fqClassName'")
    }
  }

  private fun printReport(
    missingMembers: Set<Member>,
    membersWithoutRelationships: Set<Member>,
    fqClassName: String,
  ) {
    println("Inconsistencies found in '$fqClassName'")

    println("- Missing class members")
    if (missingMembers.isEmpty()) {
      println("    -  None")
    } else {
      missingMembers.forEachIndexed { idx, it ->
        println("    ${idx + 1}. $it")
      }
    }

    println("- Members without relationships")
    if (membersWithoutRelationships.isEmpty()) {
      println("    -  None")
    } else {
      membersWithoutRelationships.forEachIndexed { idx, it ->
        println("    ${idx + 1}. ${it.signature}")
      }
    }
  }
}
