package io.redgreen.tumbleweed

import net.bytebuddy.jar.asm.FieldVisitor
import net.bytebuddy.jar.asm.Opcodes

class FieldScanner : FieldVisitor(Opcodes.ASM7) {
  companion object {
    fun scan(
      name: String?,
      descriptor: String?,
      outFields: MutableList<Field>,
    ): FieldVisitor {
      outFields.add(Field(name!!, Descriptor(descriptor!!)))
      return object : FieldVisitor(Opcodes.ASM7) { /* no-op */ }
    }
  }
}
