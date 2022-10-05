package io.redgreen.tumbleweed.cli.commands

import picocli.CommandLine.Command

@Command(
    name = "greet",
    description = ["prints a greeting"],
)
class GreetCommand : Runnable {
  override fun run() {
    println("Hello, world!")
  }
}
