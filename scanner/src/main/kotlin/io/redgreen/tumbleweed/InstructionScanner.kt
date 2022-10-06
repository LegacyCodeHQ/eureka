package io.redgreen.tumbleweed

import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes

class InstructionScanner {
  companion object {
    fun scan(
      topLevelType: String,
      caller: Method,
      outRelationships: MutableList<Relationship>,
    ): MethodVisitor {
      return object : MethodVisitor(Opcodes.ASM7) {
        override fun visitFieldInsn(
          opcode: Int,
          owner: String?,
          fieldName: String?,
          fieldDescriptor: String?,
        ) {
          val type = when (opcode) {
            Opcodes.GETFIELD -> Relationship.Type.Reads
            Opcodes.PUTFIELD -> Relationship.Type.Writes
            else -> null
          }
          type?.let {
            val field = Field(fieldName!!, FieldDescriptor.from(fieldDescriptor!!))
            val relationship = Relationship(caller, field, it)
            outRelationships.add(relationship)
          }
          super.visitFieldInsn(opcode, owner, fieldName, fieldDescriptor)
        }

        override fun visitMethodInsn(
          opcode: Int,
          owner: String?,
          methodName: String?,
          methodDescriptor: String?,
          isInterface: Boolean,
        ) {
          if (topLevelType == owner) {
            val callee = Method(methodName!!, MethodDescriptor(methodDescriptor!!))
            val relationship = Relationship(caller, callee, Relationship.Type.Calls)
            outRelationships.add(relationship)
          }
          super.visitMethodInsn(opcode, owner, methodName, methodDescriptor, isInterface)
        }
      }
    }
  }
}
