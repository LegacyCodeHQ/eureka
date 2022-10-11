package io.redgreen.tumbleweed

import net.bytebuddy.jar.asm.MethodVisitor

object MethodScanner {
  fun scan(
    topLevelType: String,
    methodName: String?,
    methodDescriptor: String?,
    outMethods: MutableList<Method>,
    outRelationships: MutableList<Relationship>,
  ): MethodVisitor {
    val method = Method(methodName!!, MethodDescriptor(methodDescriptor!!))
    outMethods.add(method)
    return InstructionScanner.scan(topLevelType, method, outRelationships)
  }
}
