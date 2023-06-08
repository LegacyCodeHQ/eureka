rootProject.name = "eureka"
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
  ":selenium",
)

project(":bytecode-scanner").projectDir = file("bytecode/scanner")
project(":bytecode-samples").projectDir = file("bytecode/samples")
project(":bytecode-testing").projectDir = file("bytecode/testing")

project(":build-systems-gradle").projectDir = file("build-systems/gradle")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      library("picocli", "info.picocli:picocli:4.7.3")
      library("logback", "ch.qos.logback:logback-classic:1.4.7")
      library("commonsCsv", "org.apache.commons:commons-csv:1.10.0")
      library("jproc", "org.buildobjects:jproc:2.8.2")
      library("jackson-kotlin", "com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
      library("arrow-core", "io.arrow-kt:arrow-core:1.1.2")
      library("byteBuddy", "net.bytebuddy:byte-buddy:1.14.5")

      library("ktor-server-netty", "io.ktor:ktor-server-netty:2.3.1")
      library("ktor-server-websockets", "io.ktor:ktor-server-websockets:2.3.1")
      library("ktor-server-cors", "io.ktor:ktor-server-cors:2.3.1")
    }

    create("testLibs") {
      // JUnit
      library("junit-api", "org.junit.jupiter:junit-jupiter-api:5.9.1")
      library("junit-params", "org.junit.jupiter:junit-jupiter-params:5.9.1")
      library("junit-engine", "org.junit.jupiter:junit-jupiter-engine:5.9.1")
      bundle("junit", listOf("junit-api", "junit-params", "junit-engine"))

      // assertion libraries
      library("truth", "com.google.truth:truth:1.1.4")

      library("approvalTests", "com.approvaltests:approvaltests:18.6.0")
      library("gson", "com.google.code.gson:gson:2.10.1")
    }
  }
}
