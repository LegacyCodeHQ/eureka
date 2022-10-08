package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.commands.ViewCommand
import picocli.CommandLine.Command

@Command(
  name = "tumbleweed",
  subcommands = [
    ViewCommand::class,
  ],
)
class TumbleweedCommand
