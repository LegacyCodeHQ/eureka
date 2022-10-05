package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.commands.GreetCommand
import picocli.CommandLine.Command

@Command(
  name = "tumbleweed",
  subcommands = [
    GreetCommand::class
  ],
)
class TumbleweedCommand
