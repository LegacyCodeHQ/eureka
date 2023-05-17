import com.github.gradle.node.npm.task.NpmTask

apply(plugin = "com.github.node-gradle.node")

tasks.register<NpmTask>("runBuild") {
  description = "Creates an optimized production build."
  args.set(listOf("run", "build"))
}

tasks.register<Copy>("copyWebClientToServer") {
  description = "Builds and copies the web client to the web-server module."
  dependsOn("runBuild", "deleteWebClientFiles", "copyWebClientFiles")
}

val webClientBuildDirectory = File("./build/")
val webClientStaticDirectory = File("./build/static/")

val webServerResourcesDirectory = project.rootDir.resolve(File("web-server/src/main/resources/"))
val webServerResourcesStaticDirectory = project.rootDir.resolve(File("web-server/src/main/resources/static"))

tasks.register<DefaultTask>("copyWebClientFiles") {
  mustRunAfter("deleteWebClientFiles")
  dependsOn("copyCss", "copyJs", "copyIndexHtml")
}

tasks.register<Copy>("copyCss") {
  from(webClientStaticDirectory.resolve("css")) {
    include("main.*.css")
  }
  into(webServerResourcesStaticDirectory.resolve("css"))
}

tasks.register<Copy>("copyJs") {
  from(webClientStaticDirectory.resolve("js")) {
    include("main.*.js")
  }
  into(webServerResourcesStaticDirectory.resolve("js"))
}

tasks.register<Copy>("copyIndexHtml") {
  from(webClientBuildDirectory) {
    include("index.html")
  }
  into(webServerResourcesDirectory)
}

tasks.register<DefaultTask>("deleteWebClientFiles") {
  mustRunAfter("runBuild")
  dependsOn("deleteCss", "deleteJs")
}

tasks.register<Delete>("deleteCss") {
  delete(fileTree(webServerResourcesStaticDirectory.resolve("css")) {
    include("main.*.css")
  })
}

tasks.register<Delete>("deleteJs") {
  delete(fileTree(webServerResourcesStaticDirectory.resolve("js")) {
    include("main.*.js")
  })
}
