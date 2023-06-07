package com.legacycode.eureka

object Opcodes {
  const val iconst_m1 = 0x02
  const val iconst_0 = 0x03
  const val iconst_1 = 0x04
  const val iconst_2 = 0x05
  const val iconst_3 = 0x06
  const val iconst_4 = 0x07
  const val iconst_5 = 0x08

  const val bipush = 0x10

  const val if_icmpne = 0xa0

  const val areturn = 0xb0
  const val `return` = 0xb1
  const val getstatic = 0xb2
  const val putstatic = 0xb3
  const val getfield = 0xb4
  const val putfield = 0xb5
  const val invokevirtual = 0xb6
  const val invokespecial = 0xb7
  const val invokestatic = 0xb8
  const val invokeinterface = 0xb9

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
