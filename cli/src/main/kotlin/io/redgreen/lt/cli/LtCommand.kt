package io.redgreen.lt.cli

import io.redgreen.lt.cli.commands.GreetCommand
import picocli.CommandLine.Command

@Command(
  name = "lt",
  subcommands = [
    GreetCommand::class
  ],
)
class LtCommand
