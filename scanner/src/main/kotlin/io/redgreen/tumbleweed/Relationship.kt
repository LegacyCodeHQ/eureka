package io.redgreen.tumbleweed

import net.bytebuddy.jar.asm.Opcodes

data class Relationship(
  val source: Member,
  val target: Member,
  val type: Type,
) {
  enum class Type {
    Reads, Writes, Calls;

    companion object {
      fun from(opcode: Int): Type {
        return when (opcode) {
          Opcodes.GETFIELD -> Reads
          Opcodes.PUTFIELD -> Writes
          else -> Calls
        }
      }
    }
  }
}
