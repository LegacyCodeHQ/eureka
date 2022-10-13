package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.ClassScanner.ASM_API_VERSION
import net.bytebuddy.jar.asm.MethodVisitor

object InstructionScanner {
  fun scan(
    topLevelType: String,
    caller: Method,
    outRelationships: MutableList<Relationship>,
  ): MethodVisitor {
    return object : MethodVisitor(ASM_API_VERSION) {
      override fun visitFieldInsn(
        opcode: Int,
        owner: String?,
        fieldName: String?,
        fieldDescriptor: String?,
      ) {
        val field = Field(fieldName!!, FieldDescriptor.from(fieldDescriptor!!))
        val relationship = Relationship(caller, field, Relationship.Type.from(opcode))
        if (owner == topLevelType) {
          outRelationships.add(relationship)
        }
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
          val relationship = Relationship(caller, callee, Relationship.Type.from(opcode))
          outRelationships.add(relationship)
        }
      }
    }
  }
}
