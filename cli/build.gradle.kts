import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  application
  id("com.github.johnrengelman.shadow") version "6.0.0"
}

application {
  applicationName = "tumbleweed"
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
    archiveBaseName.set("tumbleweed")
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
