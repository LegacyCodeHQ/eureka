package io.redgreen.tumbleweed.cli

import io.redgreen.tumbleweed.cli.commands.watch.WatchCommand
import io.redgreen.tumbleweed.cli.commands.dev.json.JsonCommand
import io.redgreen.tumbleweed.cli.commands.dev.view.ViewCommand
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
