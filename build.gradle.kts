import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
  apply(from = ".buildscripts/git-hooks.gradle")
}

plugins {
  kotlin("jvm") version ("2.1.10") apply false
  id("com.github.ben-manes.versions") version ("0.42.0") apply false
  id("io.gitlab.arturbosch.detekt") version ("1.23.7") apply false
  id("com.github.node-gradle.node") version ("5.0.0") apply false
  id("org.jetbrains.kotlinx.kover") version ("0.9.1") apply false
}

allprojects {
  group = "com.legacycode"

  repositories {
    mavenCentral()
  }
}

subprojects {
  if (this.name == "web-client-react") {
    return@subprojects
  }

  apply(plugin = "com.github.ben-manes.versions")
  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "org.jetbrains.kotlinx.kover")

  if (!isBytecodeSample()) {
    apply(plugin = "io.gitlab.arturbosch.detekt")
  }

  dependencies {
    val implementation by configurations
    val testImplementation by configurations
    val testRuntimeOnly by configurations

    // logging
    implementation(rootProject.libs.logback)

    // testing
    testImplementation(kotlin("test-junit5"))
    testImplementation(rootProject.testLibs.junit.api)
    testImplementation(rootProject.testLibs.junit.params)
    testRuntimeOnly(rootProject.testLibs.junit.engine)

    testImplementation(rootProject.testLibs.truth)

    testImplementation(rootProject.testLibs.approvalTests)
    testImplementation(rootProject.testLibs.gson) /* Used by Approvals for pretty-printing JSON */
  }

  tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  tasks.withType<JacocoReport> {
    dependsOn(tasks.withType(Test::class.java))

    reports {
      csv.required.set(false)
      xml.required.set(true)
    }
  }
}

fun isStable(version: String): Boolean {
  val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
  val regex = "^[0-9,.v-]+(-r)?$".toRegex()
  return stableKeyword || regex.matches(version)
}

tasks.withType<DependencyUpdatesTask> {
  rejectVersionIf { !isStable(candidate.version) || !isStable(currentVersion) }
}

fun Project.isBytecodeSample(): Boolean {
  return this.name.startsWith("bytecode-samples-", true)
}
