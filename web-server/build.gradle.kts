dependencies {
  implementation(project(":bytecode-scanner"))
  implementation(project(":filesystem"))

  implementation("io.ktor:ktor-server-netty:2.1.2")
  implementation("io.ktor:ktor-server-websockets:2.1.2")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
}
