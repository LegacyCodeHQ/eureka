plugins {
  application
}

dependencies {
  implementation("info.picocli:picocli:4.6.3")
}

application {
  mainClass.set("io.redgreen.lt.cli.MainKt")
}
