package com.legacycode.tumbleweed

import com.legacycode.tumbleweed.Opcodes.areturn
import com.legacycode.tumbleweed.Opcodes.iconst_0
import com.legacycode.tumbleweed.Opcodes.iconst_1
import com.legacycode.tumbleweed.Opcodes.iconst_2
import com.legacycode.tumbleweed.Opcodes.iconst_3
import com.legacycode.tumbleweed.Opcodes.iconst_4
import com.legacycode.tumbleweed.Opcodes.iconst_5
import com.legacycode.tumbleweed.Opcodes.iconst_m1
import com.legacycode.tumbleweed.Opcodes.invokespecial
import net.bytebuddy.jar.asm.Handle
import net.bytebuddy.jar.asm.Label
import net.bytebuddy.jar.asm.MethodVisitor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

typealias Opcode = Int

object InstructionScanner {
  private val logger: Logger = LoggerFactory.getLogger(InstructionScanner::class.java)

  fun scan(
    topLevelType: QualifiedType,
    caller: Method,
    constantPool: ConstantPool,
    outRelationships: MutableList<Relationship>,
  ): MethodVisitor {
    return object : MethodVisitor(ASM_API_VERSION) {
      private var maybeConstantFieldReferencedByInsn: Field? = null

      override fun visitFieldInsn(
        opcode: Opcode,
        owner: String,
        fieldName: String,
        fieldDescriptor: String,
      ) {
        logger.debug("Visiting field instruction ({}): {}/{}", opcode.instruction, owner, fieldName)

        val field = Field(fieldName, FieldDescriptor.from(fieldDescriptor), QualifiedType(owner))
        val relationship = Relationship(caller, field, Relationship.Type.from(opcode))
        if (owner == topLevelType.name) {
          logger.debug("Adding relationship: {}", relationship)
          outRelationships.add(relationship)
        } else {
          logger.debug("Skipping relationship: {}", relationship)
        }
      }

      override fun visitMethodInsn(
        opcode: Opcode,
        owner: String,
        methodName: String,
        methodDescriptor: String,
        isInterface: Boolean,
      ) {
        logger.debug("Visiting method instruction ({}): {}/{}", opcode.instruction, owner, methodName)

        if (topLevelType.name.startsWith(owner) || owner.startsWith("${topLevelType.name}\$")) {
          val callee = Method(methodName, MethodDescriptor(methodDescriptor), QualifiedType(owner))
          val relationship = Relationship(caller, callee, Relationship.Type.from(opcode))

          if (caller.name == "<clinit>" && opcode == invokespecial) {
            logger.debug("Skipping synthetic bridge call from static block: {}", relationship)
          } else {
            logger.debug("Adding relationship: {}", relationship)
            outRelationships.add(relationship)
          }
        } else {
          logger.debug("Skipping method instruction ({}): {}/{}", opcode.instruction, owner, methodName)
        }

        if (maybeConstantFieldReferencedByInsn != null) {
          val relationship = Relationship(caller, maybeConstantFieldReferencedByInsn!!, Relationship.Type.Reads)

          logger.debug("Adding relationship: {}", relationship)
          outRelationships.add(relationship)
          maybeConstantFieldReferencedByInsn = null
        }
      }

      override fun visitInvokeDynamicInsn(
        name: String?,
        descriptor: String?,
        bootstrapMethodHandle: Handle?,
        vararg bootstrapMethodArguments: Any?,
      ) {
        logger.debug("Visiting invoke dynamic instruction: {}, {}", name, bootstrapMethodArguments.contentToString())

        if (bootstrapMethodArguments.size > 1) {
          val methodHandle = bootstrapMethodArguments[1] as Handle
          val callee = Method(methodHandle.name, MethodDescriptor(methodHandle.desc), topLevelType)
          outRelationships.add(Relationship(caller, callee, Relationship.Type.Calls))
        } else {
          logger.debug("Ignoring dynamic instruction: {}{}", name, descriptor)
        }
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, *bootstrapMethodArguments)
      }

      override fun visitLdcInsn(value: Any?) {
        logger.debug("Visiting ldc instruction: {}", value)

        maybeConstantFieldReferencedByInsn = constantPool[value]
        super.visitLdcInsn(value)
      }

      override fun visitIntInsn(opcode: Opcode, operand: Int) {
        logger.debug("Visiting int instruction: {}", opcode.instruction)

        maybeConstantFieldReferencedByInsn = constantPool[operand]
        super.visitIntInsn(opcode, operand)
      }

      override fun visitInsn(opcode: Opcode) {
        logger.debug("Visiting instruction: {}", opcode.instruction)

        if (opcode == iconst_m1) {
          maybeConstantFieldReferencedByInsn = constantPool[-1]
        } else if (Opcodes.isIntInsn(opcode)) {
          maybeConstantFieldReferencedByInsn = constantPool[opcode - iconst_0]
        }

        if (opcode == areturn && maybeConstantFieldReferencedByInsn != null) {
          val relationship = Relationship(caller, maybeConstantFieldReferencedByInsn!!, Relationship.Type.Reads)
          logger.debug("Adding relationship: {}", relationship)
          outRelationships.add(relationship)
          maybeConstantFieldReferencedByInsn = null
        }
        super.visitInsn(opcode)
      }

      override fun visitJumpInsn(opcode: Opcode, label: Label?) {
        logger.debug("Visiting jump instruction: {}", opcode.instruction)

        if (maybeConstantFieldReferencedByInsn != null) {
          val relationship = Relationship(caller, maybeConstantFieldReferencedByInsn!!, Relationship.Type.Reads)

          logger.debug("Adding relationship: {}", relationship)
          outRelationships.add(relationship)
          maybeConstantFieldReferencedByInsn = null
        }
        super.visitJumpInsn(opcode, label)
      }
    }
  }

  @Suppress("MagicNumber")
  internal val Opcode.instruction: String
    get() {
      return when (this) {
        0xb1 -> "return"
        0xb2 -> "getstatic"
        0xb3 -> "putstatic"
        0xb5 -> "putfield"
        0xb4 -> "getfield"
        invokespecial -> "invokespecial"
        0xb6 -> "invokevirtual"
        0xb8 -> "invokestatic"
        0xb9 -> "invokeinterface"
        0x10 -> "bipush"
        iconst_m1 -> "iconst_m1"
        iconst_0 -> "iconst_0"
        iconst_1 -> "iconst_1"
        iconst_2 -> "iconst_2"
        iconst_3 -> "iconst_3"
        iconst_4 -> "iconst_4"
        iconst_5 -> "iconst_5"
        0xa0 -> "if_icmpne"
        0xb0 -> "areturn"
        else -> "unmapped (${"0x%02x".format(this)})})"
      }
    }
}
