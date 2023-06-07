package com.legacycode.eureka

object Opcodes {
  const val iconst_m1 = 0x02
  const val iconst_0 = 0x03
  const val iconst_1 = 0x04
  const val iconst_2 = 0x05
  const val iconst_3 = 0x06
  const val iconst_4 = 0x07
  const val iconst_5 = 0x08
  const val areturn = 0xb0

  const val invokespecial = 0xb7

  fun isIntInsn(opcode: Int): Boolean {
    return opcode == iconst_m1 ||
      opcode == iconst_0 ||
      opcode == iconst_1 ||
      opcode == iconst_2 ||
      opcode == iconst_3 ||
      opcode == iconst_4 ||
      opcode == iconst_5
  }
}
