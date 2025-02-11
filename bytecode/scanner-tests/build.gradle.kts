kotlin {
  jvmToolchain(17)
}

dependencies {
  testImplementation(projects.bytecodeScanner)
  testImplementation(projects.bytecodeSamplesJava)
  testImplementation(projects.bytecodeSamplesKotlin)
  testImplementation(projects.bytecodeTesting)
}
