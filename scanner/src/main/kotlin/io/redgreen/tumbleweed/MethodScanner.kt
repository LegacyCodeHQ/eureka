package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.Relationship.Type
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
          val type = when (opcode) {
            Opcodes.GETFIELD -> Type.Reads
            Opcodes.PUTFIELD -> Type.Writes
            else -> null
          }
          type?.let {
            val field = Field(fieldName!!, FieldDescriptor.from(fieldDescriptor!!))
            val relationship = Relationship(method, field, it)
            outRelationships.add(relationship)
          }
          super.visitFieldInsn(opcode, owner, fieldName, fieldDescriptor)
        }
      }
    }
  }
}
