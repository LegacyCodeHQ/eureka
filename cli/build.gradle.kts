import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.util.Properties
import org.jreleaser.model.Active

@Suppress("PropertyName")
val CLI_VERSION = "0.71.0-SNAPSHOT"

plugins {
  application
  id("com.github.johnrengelman.shadow") version "7.1.2"
  id("org.jreleaser") version "1.2.0"
}

application {
  applicationName = "eureka"
  mainClassName = "com.legacycode.eureka.cli.MainKt"
}

tasks.withType<Jar> {
  manifest {
    attributes(
      "Main-Class" to "com.legacycode.eureka.cli.MainKt",
    )
  }
}

tasks {
  named<ShadowJar>("shadowJar") {
    archiveBaseName.set("eureka")
    mergeServiceFiles()
  }

  build {
    dependsOn(shadowJar)
  }
}

tasks {
  named("classes") {
    dependsOn("createVersionFile")
  }

  create("createVersionFile") {
    dependsOn(processResources)
    doLast {
      File("$buildDir/resources/main/version.properties").bufferedWriter().use { writer ->
        val properties = Properties()
        properties["version"] = CLI_VERSION
        properties.store(writer, null)
      }
    }
  }
}

dependencies {
  implementation(projects.webServer)
  implementation(projects.filesystem)
  implementation(projects.bytecodeScanner) /* only for the `json` debug command */
  implementation(projects.viz)
  implementation(projects.android)
  implementation(projects.buildSystemsGradle)
  implementation(projects.vcs)

  implementation(libs.commonsCsv)
  implementation(libs.picocli)
  implementation(libs.picnic)

  testImplementation(projects.bytecodeTesting)
}

/* Ported from https://github.com/mobile-dev-inc/maestro/blob/main/maestro-cli/build.gradle */
jreleaser {
  version = CLI_VERSION
  gitRootSearch.set(true)

  project {
    name.set("Eureka")
    description.set("Understand and break down large classes without breaking a sweat.")
    website.set("https://github.com/LegacyCodeHQ/eureka")
    license.set("Apache-2.0")
    copyright.set("2022-Present Ragunath Jawahar")
  }

  release {
    github {
      repoOwner.set("LegacyCodeHQ")
      name.set("eureka")
      branch.set("main")
      repoUrl.set("git@github.com:LegacyCodeHQ/eureka.git")

      tagName.set("eureka-$CLI_VERSION")
      releaseName.set("Eureka $CLI_VERSION")
      overwrite.set(true)

      token.set(System.getenv("GITHUB_TOKEN"))
    }
  }

  distributions {
    create("eureka") {
      artifact {
        path.set(File("build/distributions/eureka-$CLI_VERSION.zip"))
      }

      brew {
        active.set(Active.RELEASE)
        formulaName.set("eureka")

        repoTap {
          repoOwner.set("LegacyCodeHQ")
          name.set("homebrew-tap")
        }

        dependencies {
          dependency("openjdk@11")
        }

        extraProperties.put("skipJava", true)
      }
    }
  }
}

tasks.register<DefaultTask>("promoteSnapshotVersion") {
  description = "Promotes the SNAPSHOT version to production."

  doLast {
    val currentVersion = CLI_VERSION
    val newVersion = currentVersion.removeSuffix("-SNAPSHOT")

    updateVersion(currentVersion, newVersion)

    println("Promoted version: $currentVersion -> $newVersion")
  }
}

val publicReleaseTaskGroup = "Public release"

tasks.register<DefaultTask>("prepareRelease") {
  group = publicReleaseTaskGroup
  description = "Promotes snapshot version, builds latest web client, and prepares tool for release."

  dependsOn(":web-client-react:copyWebClientToServer", "promoteSnapshotVersion")

  doLast {
    exec {
      commandLine("git", "add", "-A")
    }

    exec {
      commandLine("git", "commit", "-am", "build: prepare release")
    }
  }
}

tasks.register<DefaultTask>("release") {
  group = publicReleaseTaskGroup
  description = "Publishes the latest version of the tool on GitHub and Homebrew."

  dependsOn("jreleaserFullRelease", "uploadRelease")
}

tasks.register<DefaultTask>("uploadRelease") {
  mustRunAfter("jreleaserFullRelease")
  dependsOn("jreleaserUpload")
}

tasks.register<DefaultTask>("nextIteration") {
  group = publicReleaseTaskGroup
  description = "Updates the version name for the next iteration."

  doLast {
    val currentVersion = CLI_VERSION
    val (major, minor, _) = currentVersion.split('.').map(String::toInt)
    val nextVersion = arrayOf(major, minor + 1, 0).joinToString(".") + "-SNAPSHOT"

    updateVersion(currentVersion, nextVersion)
    println("Next iteration: $currentVersion -> $nextVersion")

    exec {
      commandLine("git", "add", "-A")
    }

    exec {
      commandLine("git", "commit", "-am", "build: prepare next iteration")
    }
  }
}

fun updateVersion(currentVersion: String, nextVersion: String) {
  val updatedBuildScript = File("cli/build.gradle.kts")
    .readText()
    .replace(currentVersion, nextVersion)

  File("cli/build.gradle.kts")
    .writeText(updatedBuildScript)
}
