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
          classStructurePrintableBuilder
            .appendLine("  - ${field.signature}")
        }
      }

      if (methods.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Methods:")
        methods.forEach { method ->
          classStructurePrintableBuilder
            .appendLine("  - ${method.signature}")
        }
      }

      if (relationships.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Reads:")
        relationships.forEach { relationship ->
          classStructurePrintableBuilder
            .appendLine("  - ${relationship.method.signature} -> ${relationship.field.signature}")
        }
      }

      return classStructurePrintableBuilder.toString()
    }
  }
