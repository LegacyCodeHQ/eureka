package com.legacycode.tumbleweed.version

import java.util.Properties

class TwdProperties private constructor(private val properties: Properties) {
  companion object {
    private const val PROPERTIES_FILENAME = "version.properties"
    private const val KEY_VERSION = "version"

    fun get(): TwdProperties {
      val properties = TwdProperties::class.java.classLoader
        .getResourceAsStream(PROPERTIES_FILENAME).use { Properties().apply { load(it) } }
      return TwdProperties(properties)
    }
  }

  val version: TwdVersion
    get() {
      return TwdVersion(properties[KEY_VERSION].toString())
    }
}

@JvmInline
value class TwdVersion(val name: String) {
  override fun toString(): String {
    return "$name by https://legacycode.com"
  }
}
