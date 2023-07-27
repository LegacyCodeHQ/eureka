package com.legacycode.eureka.version

import com.legacycode.eureka.testing.TextResource
import java.util.Properties

class EurekaProperties private constructor(private val properties: Properties) {
  companion object {
    private const val PROPERTIES_FILENAME = "version.properties"
    private const val KEY_VERSION = "version"

    fun get(): EurekaProperties {
      val propertiesContent = TextResource(PROPERTIES_FILENAME).content
      val properties = Properties().apply {
        load(propertiesContent.reader())
      }
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
