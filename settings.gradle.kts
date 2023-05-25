rootProject.name = "tumbleweed"
include(
  ":cli",
  ":bytecode-scanner",
  ":bytecode-samples",
  ":web-server",
  ":filesystem",
  ":vcs",
  ":bytecode-testing",
  ":web-client-react",
  ":android",
  ":viz",
  ":build-systems-gradle",
)

project(":bytecode-scanner").projectDir = file("bytecode/scanner")
project(":bytecode-samples").projectDir = file("bytecode/samples")
project(":bytecode-testing").projectDir = file("bytecode/testing")

project(":build-systems-gradle").projectDir = file("build-systems/gradle")
