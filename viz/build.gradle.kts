dependencies {
  implementation(project(":bytecode-scanner"))

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")

  testImplementation(project(":bytecode-samples"))
  testImplementation(project(":bytecode-testing"))
}
