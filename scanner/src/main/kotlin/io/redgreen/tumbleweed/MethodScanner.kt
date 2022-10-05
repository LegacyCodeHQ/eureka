package io.redgreen.tumbleweed

import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes

class MethodScanner {
  companion object {
    fun scan(
      name: String?,
      descriptor: String?,
      signature: String?,
      outMethods: MutableList<Method>,
    ): MethodVisitor {
      outMethods.add(Method(name!!, Descriptor(descriptor!!)))
      return object : MethodVisitor(Opcodes.ASM7) { /* no-op */ }
    }
  }
}
