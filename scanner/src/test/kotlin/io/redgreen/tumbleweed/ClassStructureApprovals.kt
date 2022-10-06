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

      val readsRelationships = relationships.filter { it.type == Relationship.Type.Reads }
      if (readsRelationships.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Reads:")
        readsRelationships.forEach { relationship ->
          classStructurePrintableBuilder
            .appendLine("  - ${relationship.method.signature} -> ${relationship.field.signature}")
        }
      }

      val writesRelationships = relationships.filter { it.type == Relationship.Type.Writes }
      if (writesRelationships.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Writes:")
        writesRelationships.forEach { relationship ->
          classStructurePrintableBuilder
            .appendLine("  - ${relationship.method.signature} -> ${relationship.field.signature}")
        }
      }

      return classStructurePrintableBuilder.toString()
    }
  }
