package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.commands.WatchCommand
import io.redgreen.tumbleweed.cli.dev.JsonCommand
import io.redgreen.tumbleweed.cli.dev.view.ViewCommand
import picocli.CommandLine.Command

@Command(
  name = "twd",
  subcommands = [
    WatchCommand::class,
    JsonCommand::class,
    ViewCommand::class,
  ],
)
class TumbleweedCommand
