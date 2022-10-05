package io.redgreen.tumbleweed

val ClassStructure.printable: Any
  get() {
    with(this) {
      val classStructurePrintableBuilder = StringBuilder()

      classStructurePrintableBuilder
        .appendLine("Package: $`package`")
        .appendLine("Class: $className")

      if (fields.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Fields:")
        fields.forEach { field ->
          classStructurePrintableBuilder.appendLine("  - ${field.name}: ${field.descriptor.type}")
        }
      }

      if (methods.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Methods:")
        methods.forEach { method ->
          classStructurePrintableBuilder.appendLine("  - ${method.returnType} ${method.name}(${method.parameters.joinToString(", ")})")
        }
      }

      return classStructurePrintableBuilder.toString()
    }
  }
