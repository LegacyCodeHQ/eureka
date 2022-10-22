package io.redgreen.tumbleweed

import net.bytebuddy.jar.asm.Opcodes.GETFIELD
import net.bytebuddy.jar.asm.Opcodes.GETSTATIC
import net.bytebuddy.jar.asm.Opcodes.PUTFIELD
import net.bytebuddy.jar.asm.Opcodes.PUTSTATIC

data class Relationship(
  val source: Member,
  val target: Member,
  val type: Type,
) {
  enum class Type {
    Reads, Writes, Calls, References;

    companion object {
      fun from(opcode: Int): Type {
        return when (opcode) {
          GETFIELD, GETSTATIC -> Reads
          PUTFIELD, PUTSTATIC -> Writes
          else -> Calls
        }
      }
    }
  }
}
