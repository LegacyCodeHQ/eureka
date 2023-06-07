package com.legacycode.eureka.cli

import ch.qos.logback.classic.Logger as LogbackLogger
import ch.qos.logback.classic.Level
import com.legacycode.eureka.cli.dev.convert.ConvertCommand
import com.legacycode.eureka.cli.dev.diff.DiffCommand
import com.legacycode.eureka.cli.dev.json.JsonCommand
import com.legacycode.eureka.cli.dev.methods.MethodsCommand
import com.legacycode.eureka.cli.dev.view.ViewCommand
import com.legacycode.eureka.cli.modules.ModulesCommand
import com.legacycode.eureka.cli.ownership.OwnershipCommand
import com.legacycode.eureka.cli.watch.WatchCommand
import com.legacycode.eureka.version.TwdProperties
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
    ModulesCommand::class,

    /* dev commands */
    JsonCommand::class,
    ViewCommand::class,
    ConvertCommand::class,
    DiffCommand::class,
    MethodsCommand::class,
  ],
)
class EurekaCommand {
  @Option(
    names = ["--version", "-v"],
    description = ["prints Eureka version"],
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
  val commandLine = CommandLine(EurekaCommand())
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
