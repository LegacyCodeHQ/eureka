dependencies {
  implementation("net.bytebuddy:byte-buddy:1.14.4")

  testImplementation(project(":bytecode-samples"))
  testImplementation(project(":bytecode-testing"))
}
