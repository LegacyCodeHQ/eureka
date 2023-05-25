package com.legacycode.ureka.gradle

class Project(val name: String) {
    val subprojects: MutableList<Project> = mutableListOf()
    var parentProject: Project? = null
}
