import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  apply(from = ".buildscripts/git-hooks.gradle")
}

plugins {
  kotlin("jvm") version "1.7.10" apply false
  id("com.github.ben-manes.versions") version "0.42.0" apply false
  id("io.gitlab.arturbosch.detekt") version("1.21.0") apply false
}

allprojects {
  group = "io.redgreen"
  version = "0.1.0-SNAPSHOT"

  repositories {
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "com.github.ben-manes.versions")
  apply(plugin = "jacoco")

  if (this.name != "bytecode-samples") {
    apply(plugin = "io.gitlab.arturbosch.detekt")
  }

  dependencies {
    val implementation by configurations
    val testImplementation by configurations
    val testRuntimeOnly by configurations

    // logging
    implementation("org.slf4j:slf4j-simple:2.0.3")

    // testing
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    testImplementation("com.google.truth:truth:1.1.3")

    testImplementation("com.approvaltests:approvaltests:18.4.0")
    testImplementation("com.google.code.gson:gson:2.9.1") /* Used by Approvals for pretty-printing JSON */
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
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
