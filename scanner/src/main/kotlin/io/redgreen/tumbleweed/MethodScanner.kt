package io.redgreen.tumbleweed

import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes

class MethodScanner {
  companion object {
    fun scan(
      methodName: String?,
      methodDescriptor: String?,
      outMethods: MutableList<Method>,
      outRelationships: MutableList<Relationship>,
    ): MethodVisitor {
      val method = Method(methodName!!, MethodDescriptor(methodDescriptor!!))
      outMethods.add(method)
      return object : MethodVisitor(Opcodes.ASM7) {
        override fun visitFieldInsn(opcode: Int, owner: String?, fieldName: String?, fieldDescriptor: String?) {
          if (opcode == Opcodes.GETFIELD) {
            val relationship = Relationship.reads(method, Field(fieldName!!, FieldDescriptor.from(fieldDescriptor!!)))
            outRelationships.add(relationship)
          } else if (opcode == Opcodes.PUTFIELD) {
            val relationship = Relationship.writes(method, Field(fieldName!!, FieldDescriptor.from(fieldDescriptor!!)))
            outRelationships.add(relationship)
          }
          super.visitFieldInsn(opcode, owner, fieldName, fieldDescriptor)
        }
      }
    }
  }
}
