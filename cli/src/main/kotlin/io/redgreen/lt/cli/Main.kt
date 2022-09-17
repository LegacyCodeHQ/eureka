package io.redgreen.lt.cli

import kotlin.system.exitProcess
import picocli.CommandLine

fun main(args: Array<String>) {
  exitProcess(CommandLine(LtCommand())
    .execute(*args))
}
