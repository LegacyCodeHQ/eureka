package com.legacycode.tumbleweed.cli.dev.json

import com.legacycode.tumbleweed.ClassStructure
import com.legacycode.tumbleweed.Member

fun ClassStructure.check() {
  val membersInClassStructure = (fields + methods).toSet()
  val membersInRelationship = relationships.flatMap { listOf(it.source, it.target) }.toSet()
  val fqClassName = "$packageName.$className"

  if (membersInClassStructure != membersInRelationship) {
    val missingMembers = membersInRelationship - membersInClassStructure
    val membersWithoutRelationships = membersInClassStructure - membersInRelationship
    printReport(fqClassName, missingMembers, membersWithoutRelationships)
  } else {
    println("No inconsistencies found in '$fqClassName'")
  }
}

private fun printReport(
  fqClassName: String,
  missingMembers: Set<Member>,
  membersWithoutRelationships: Set<Member>,
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
