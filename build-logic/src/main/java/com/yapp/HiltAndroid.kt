package com.yapp

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("com.google.devtools.ksp")
    }

    val libs = extensions.libs
    dependencies {
        "implementation"(libs.findLibrary("hilt.android").get())
        "implementation"(libs.findLibrary("hilt.navigation.compose").get())
        "ksp"(libs.findLibrary("hilt.compiler").get())
        "kspTest"(libs.findLibrary("hilt.compiler").get())
    }
}

internal class HiltAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureHiltAndroid()
        }
    }
}