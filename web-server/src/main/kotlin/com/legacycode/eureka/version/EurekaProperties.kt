package com.legacycode.eureka.version

import java.util.Properties

class EurekaProperties private constructor(private val properties: Properties) {
  companion object {
    private const val PROPERTIES_FILENAME = "version.properties"
    private const val KEY_VERSION = "version"

    fun get(): EurekaProperties {
      val properties = EurekaProperties::class.java.classLoader
        .getResourceAsStream(PROPERTIES_FILENAME).use { Properties().apply { load(it) } }
      return EurekaProperties(properties)
    }
  }

  val version: EurekaVersion
    get() {
      return EurekaVersion(properties[KEY_VERSION].toString())
    }
}

@JvmInline
value class EurekaVersion(val name: String) {
  override fun toString(): String {
    return "$name by https://legacycode.com"
  }
}
