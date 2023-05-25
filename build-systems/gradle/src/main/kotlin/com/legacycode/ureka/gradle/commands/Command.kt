package com.legacycode.ureka.gradle.commands

import java.io.File
import org.buildobjects.process.ProcBuilder

interface Command {
  val text: String

  fun execute(cmdFile: File? = null): String {
    val cmdArgs = text.split(" ")
    val cmd = cmdFile?.path ?: cmdArgs.first()
    val args = cmdArgs.drop(1).toTypedArray()
    return ProcBuilder.run(cmd, *args)!!
  }
}
