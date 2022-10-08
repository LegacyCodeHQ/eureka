package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.commands.ViewCommand
import picocli.CommandLine.Command

@Command(
  name = "twd",
  subcommands = [
    ViewCommand::class,
  ],
)
class TumbleweedCommand
