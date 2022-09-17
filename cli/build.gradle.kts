import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  application
  id("com.github.johnrengelman.shadow") version "6.0.0"
}

application {
  applicationName = "lt"
  mainClassName = "io.redgreen.lt.cli.MainKt"
}

tasks.withType<Jar> {
  manifest {
    attributes(
      "Main-Class" to "io.redgreen.lt.cli.MainKt",
    )
  }
}

tasks {
  named<ShadowJar>("shadowJar") {
    archiveBaseName.set("lt")
    mergeServiceFiles()
  }
}

tasks {
  build {
    dependsOn(shadowJar)
  }
}

dependencies {
  implementation("info.picocli:picocli:4.6.3")
}
