import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.util.Properties
import org.jreleaser.model.Active

@Suppress("PropertyName")
val CLI_VERSION = "0.39.0-SNAPSHOT"

plugins {
  application
  id("com.github.johnrengelman.shadow") version "7.1.2"
  id("org.jreleaser") version "1.2.0"
}

application {
  applicationName = "twd"
  mainClassName = "io.redgreen.tumbleweed.cli.MainKt"
}

tasks.withType<Jar> {
  manifest {
    attributes(
      "Main-Class" to "io.redgreen.tumbleweed.cli.MainKt",
    )
  }
}

tasks {
  named<ShadowJar>("shadowJar") {
    archiveBaseName.set("twd")
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
  implementation(project(":web-server"))
  implementation(project(":filesystem"))
  implementation(project(":bytecode:scanner")) /* only for the `json` debug command */

  implementation("org.apache.commons:commons-csv:1.9.0")

  implementation("info.picocli:picocli:4.7.0")
}

/* Ported from https://github.com/mobile-dev-inc/maestro/blob/main/maestro-cli/build.gradle */
jreleaser {
  version = CLI_VERSION
  gitRootSearch.set(true)

  project {
    name.set("Tumbleweed")
    description.set("Understand and break down large classes without breaking a sweat.")
    website.set("https://github.com/redgreenio/tumbleweed")
    license.set("Apache-2.0")
    copyright.set("2022-Present Ragunath Jawahar")
  }

  release {
    github {
      repoOwner.set("redgreenio")
      name.set("tumbleweed")
      branch.set("main")
      repoUrl.set("git@github.com:redgreenio/tumbleweed.git")

      tagName.set("twd-$CLI_VERSION")
      releaseName.set("TWD $CLI_VERSION")
      overwrite.set(true)

      token.set(System.getenv("GITHUB_TOKEN"))
    }
  }

  distributions {
    create("twd") {
      artifact {
        path.set(File("build/distributions/twd-$CLI_VERSION.zip"))
      }

      brew {
        active.set(Active.RELEASE)
        formulaName.set("twd")

        repoTap {
          repoOwner.set("redgreenio")
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
