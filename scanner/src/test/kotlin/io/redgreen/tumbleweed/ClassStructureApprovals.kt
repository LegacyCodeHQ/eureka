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
          classStructurePrintableBuilder.appendLine("  - ${field.name}: ${field.type.name}")
        }
      }

      if (methods.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Methods:")
        methods.forEach { method ->
          classStructurePrintableBuilder.appendLine("  - ${method.signature}")
        }
      }

      return classStructurePrintableBuilder.toString()
    }
  }
