package com.legacycode.eureka.gradle.metrics

import com.legacycode.eureka.gradle.Project
import com.legacycode.eureka.gradle.ProjectStructure
import com.legacycode.eureka.gradle.SubprojectDependency

private val structure = ProjectStructure(
  Project("tumbleweed"),
  listOf(
    "android",
    "build-systems-gradle",
    "bytecode-samples",
    "bytecode-scanner",
    "bytecode-testing",
    "cli",
    "filesystem",
    "vcs",
    "viz",
    "web-client-react",
    "web-server",
  ).map(::Project),
)

private val resolvedDependencies = mapOf(
  Project("android") to subprojectDependencies(
    "bytecode-scanner", "viz", "bytecode-testing",
  ),

  Project("build-systems-gradle") to subprojectDependencies(),

  Project("bytecode-samples") to subprojectDependencies(),

  Project("bytecode-scanner") to subprojectDependencies(
    "bytecode-samples", "bytecode-testing",
  ),

  Project("bytecode-testing") to subprojectDependencies(),

  Project("cli") to subprojectDependencies(
    "web-server", "filesystem", "bytecode-scanner", "viz",
    "android", "build-systems-gradle", "bytecode-testing",
  ),

  Project("filesystem") to subprojectDependencies(
    "bytecode-samples",
  ),

  Project("vcs") to subprojectDependencies(),

  Project("viz") to subprojectDependencies(
    "bytecode-scanner", "bytecode-samples", "bytecode-testing",
  ),

  Project("web-client-react") to subprojectDependencies(),

  Project("web-server") to subprojectDependencies(
    "bytecode-scanner", "filesystem", "vcs", "android", "viz",
  ),
)

val eureka = structure to resolvedDependencies

private fun subprojectDependencies(
  vararg names: String,
): List<SubprojectDependency> {
  return names.map(::SubprojectDependency).toList()
}
