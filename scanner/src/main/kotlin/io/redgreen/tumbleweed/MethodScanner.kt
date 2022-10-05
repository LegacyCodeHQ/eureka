package io.redgreen.tumbleweed

import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes

class MethodScanner {
  companion object {
    fun scan(
      name: String?,
      descriptor: String?,
      outMethods: MutableList<Method>,
    ): MethodVisitor {
      outMethods.add(Method(name!!, MethodDescriptor(descriptor!!)))
      return object : MethodVisitor(Opcodes.ASM7) { /* no-op */ }
    }
  }
}
