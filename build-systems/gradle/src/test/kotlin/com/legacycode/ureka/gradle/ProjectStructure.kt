package com.legacycode.ureka.gradle

import com.legacycode.eureka.gradle.ProjectStructure
import org.approvaltests.strings.Printable

val ProjectStructure.printable: Printable<ProjectStructure>
  get() {
    fun getIndent(index: Int): String {
      return if (index == subprojects.lastIndex) {
        "\\--- "
      } else {
        "+--- "
      }
    }

    return Printable(this) {
      val projectStructureBuilder = StringBuilder()

      projectStructureBuilder.appendLine("Root project '${it.rootProject.name}'")
      it.subprojects.forEachIndexed { index, project ->
        val indent = getIndent(index)

        projectStructureBuilder.appendLine("${indent}Project ':${project.name}'")

      }
      projectStructureBuilder.toString()
    }
  }
