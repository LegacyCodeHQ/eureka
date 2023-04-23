dependencies {
  implementation(project(":bytecode:scanner"))
  implementation(project(":filesystem"))
  implementation(project(":vcs"))

  implementation("io.ktor:ktor-server-netty:2.1.3")
  implementation("io.ktor:ktor-server-websockets:2.1.3")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
  implementation("com.spotify.mobius:mobius-core:1.5.11")
  implementation("com.spotify.mobius:mobius-rx3:1.5.11")

  testImplementation(project(":bytecode:samples"))
  testImplementation(project(":bytecode:testing"))
  testImplementation("com.spotify.mobius:mobius-test:1.5.11")
}
