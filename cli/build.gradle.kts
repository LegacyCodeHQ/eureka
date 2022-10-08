import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  application
  id("com.github.johnrengelman.shadow") version "6.0.0"
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
}

tasks {
  build {
    dependsOn(shadowJar)
  }
}

dependencies {
  implementation(project(":web-server"))
  implementation(project(":filesystem"))

  implementation("info.picocli:picocli:4.6.3")
}
