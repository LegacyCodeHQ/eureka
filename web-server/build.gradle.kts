dependencies {
  implementation(project(":bytecode-scanner"))
  implementation(project(":filesystem"))
  implementation(project(":vcs"))
  implementation(project(":android"))
  implementation(project(":viz"))

  implementation("io.ktor:ktor-server-netty:2.1.3")
  implementation("io.ktor:ktor-server-websockets:2.1.3")
  implementation("io.ktor:ktor-server-cors:2.1.3")

  implementation("com.spotify.mobius:mobius-core:1.5.11")
  implementation("com.spotify.mobius:mobius-rx3:1.5.11")

  testImplementation("com.spotify.mobius:mobius-test:1.5.11")
}
