dependencies {
  implementation(project(":bytecode-scanner"))

  implementation(libs.jackson.kotlin)

  testImplementation(project(":bytecode-samples"))
  testImplementation(project(":bytecode-testing"))
}
