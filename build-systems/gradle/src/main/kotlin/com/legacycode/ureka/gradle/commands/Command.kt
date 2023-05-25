package com.legacycode.ureka.gradle.commands

import org.buildobjects.process.ProcBuilder

interface Command {
  val text: String

  fun execute(): String {
    val cmdArgs = text.split(" ")
    val cmd = cmdArgs.first()
    val args = cmdArgs.drop(1).toTypedArray()
    return ProcBuilder.run(cmd, *args)!!
  }
}
