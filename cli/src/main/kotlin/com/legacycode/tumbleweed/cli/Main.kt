package com.legacycode.tumbleweed.cli

import ch.qos.logback.classic.Logger as LogbackLogger
import ch.qos.logback.classic.Level
import com.legacycode.tumbleweed.cli.dev.convert.ConvertCommand
import com.legacycode.tumbleweed.cli.dev.diff.DiffCommand
import com.legacycode.tumbleweed.cli.dev.json.JsonCommand
import com.legacycode.tumbleweed.cli.dev.methods.MethodsCommand
import com.legacycode.tumbleweed.cli.dev.view.ViewCommand
import com.legacycode.tumbleweed.cli.ownership.OwnershipCommand
import com.legacycode.tumbleweed.cli.watch.WatchCommand
import com.legacycode.tumbleweed.version.TwdProperties
import kotlin.system.exitProcess
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option

const val DEFAULT_PORT = 7070

@Command(
  name = "twd",
  subcommands = [
    WatchCommand::class,
    OwnershipCommand::class,

    /* dev commands */
    JsonCommand::class,
    ViewCommand::class,
    ConvertCommand::class,
    DiffCommand::class,
    MethodsCommand::class,
  ],
)
class TumbleweedCommand {
  @Option(
    names = ["--version", "-v"],
    description = ["prints Tumbleweed version"],
    versionHelp = true,
  )
  var version: Boolean = false

  @Suppress("unused") // Used by picocli.
  @Option(
    names = ["--debug", "-d"],
    description = ["turns on debug logging"],
    defaultValue = "false",
  )
  fun setDebug(debug: Boolean) {
    val logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as LogbackLogger
    val level = if (debug) Level.DEBUG else Level.INFO
    logger.level = level
    logger.debug("Debug mode set to {}", debug)
  }
}

fun main(args: Array<String>) {
  val commandLine = CommandLine(TumbleweedCommand())
  val exitCode = commandLine.execute(*args)

  if (commandLine.isVersionHelpRequested) {
    printVersion()
    exitProcess(0)
  }

  exitProcess(exitCode)
}

private fun printVersion() {
  println(TwdProperties.get().version)
}
