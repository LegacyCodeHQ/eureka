package com.legacycode.eureka

val ClassStructure.printable: Any
  get() {
    with(this) {
      val classStructurePrintableBuilder = StringBuilder()

      classStructurePrintableBuilder
        .appendLine("Package: $packageName")
        .appendLine("Class: $className")

      classStructurePrintableBuilder
        .appendLine("Extends: ${superType.name}")

      if (interfaces.isNotEmpty()) {
        classStructurePrintableBuilder
          .appendLine("Implements: ${interfaces.joinToString(", ") { it.name }}")
      }

      if (fields.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Fields:")
        fields.forEach { field ->
          classStructurePrintableBuilder
            .appendLine("  - ${field.signature.verbose}")
        }
      }

      if (methods.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Methods:")
        methods.forEach { method ->
          classStructurePrintableBuilder
            .appendLine("  - ${method.signature.verbose}")
        }
      }

      val readsRelationships = relationships.filter { it.type == Relationship.Type.Reads }
      if (readsRelationships.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Reads:")
        readsRelationships.forEach { relationship ->
          classStructurePrintableBuilder
            .appendLine("  - ${relationship.source.signature.verbose} -> ${relationship.target.signature.verbose}")
        }
      }

      val writesRelationships = relationships.filter { it.type == Relationship.Type.Writes }
      if (writesRelationships.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Writes:")
        writesRelationships.forEach { relationship ->
          classStructurePrintableBuilder
            .appendLine("  - ${relationship.source.signature.verbose} -> ${relationship.target.signature.verbose}")
        }
      }

      val callsRelationships = relationships.filter { it.type == Relationship.Type.Calls }
      if (callsRelationships.isNotEmpty()) {
        classStructurePrintableBuilder.appendLine("Calls:")
        callsRelationships.forEach { relationship ->
          classStructurePrintableBuilder
            .appendLine("  - ${relationship.source.signature.verbose} -> ${relationship.target.signature.verbose}")
        }
      }

      return classStructurePrintableBuilder.toString()
    }
  }
