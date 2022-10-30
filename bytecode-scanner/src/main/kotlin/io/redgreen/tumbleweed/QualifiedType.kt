package io.redgreen.tumbleweed

/*
 * FIXME: This class represents both external and internal qualified name
 * FIXME: types without a distinction. (e.g. java.lang.Object and java/lang/Object)
 */
@JvmInline
value class QualifiedType(val name: String) {
  val simpleName: String
    get() {
      val isArray = name.startsWith("[]")
      val lastDotIndex = name.lastIndexOf(".")
      val isPrimitive = lastDotIndex == -1

      val simpleName = if (isPrimitive) {
        name
      } else {
        val possiblySimpleName = name.substring(lastDotIndex + 1)
        val lastDollarIndex = possiblySimpleName.lastIndexOf('$')
        if (lastDollarIndex == -1) possiblySimpleName else possiblySimpleName.substring(lastDollarIndex + 1)
      }

      return if (isPrimitive && isArray) {
        simpleName
      } else if (isArray) {
        name.substring(0, name.lastIndexOf("]") + 1) + simpleName
      } else {
        simpleName
      }
    }
}
