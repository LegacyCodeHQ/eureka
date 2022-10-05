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
      println("Method: $name, $descriptor, $signature")
      outMethods.add(Method(name!!))
      return object : MethodVisitor(Opcodes.ASM7) { /* no-op */ }
    }
  }
}
