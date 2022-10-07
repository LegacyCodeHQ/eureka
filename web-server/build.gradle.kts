dependencies {
  implementation(project(":bytecode-scanner"))
  implementation(project(":filesystem"))

  implementation("io.ktor:ktor-server-netty:2.1.2")
  implementation("io.ktor:ktor-server-websockets:2.1.2")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")

  testImplementation(project(":bytecode-samples"))
  testImplementation("com.google.code.gson:gson:2.9.0") /* Used by Approvals for pretty-printing JSON */
}
