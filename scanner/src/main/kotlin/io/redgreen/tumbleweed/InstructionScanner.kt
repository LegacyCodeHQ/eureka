package io.redgreen.tumbleweed

import net.bytebuddy.jar.asm.MethodVisitor
import net.bytebuddy.jar.asm.Opcodes

class InstructionScanner {
  companion object {
    fun scan(method: Method, outRelationships: MutableList<Relationship>): MethodVisitor {
      return object : MethodVisitor(Opcodes.ASM7) {
        override fun visitFieldInsn(opcode: Int, owner: String?, fieldName: String?, fieldDescriptor: String?) {
          val type = when (opcode) {
            Opcodes.GETFIELD -> Relationship.Type.Reads
            Opcodes.PUTFIELD -> Relationship.Type.Writes
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
