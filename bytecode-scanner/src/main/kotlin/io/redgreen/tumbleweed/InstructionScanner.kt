package io.redgreen.tumbleweed

import io.redgreen.tumbleweed.ClassScanner.ASM_API_VERSION
import net.bytebuddy.jar.asm.MethodVisitor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object InstructionScanner {
  private val logger: Logger = LoggerFactory.getLogger(InstructionScanner::class.java)

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
        logger.debug("Visiting field instruction ({}): {}/{}", opcode, owner, fieldName)

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
        logger.debug("Visiting method instruction ({}): {}/{}", opcode, owner, methodName)

        if (topLevelType == owner) {
          val callee = Method(methodName!!, MethodDescriptor(methodDescriptor!!))
          val relationship = Relationship(caller, callee, Relationship.Type.from(opcode))
          outRelationships.add(relationship)
        }
      }
    }
  }
}
