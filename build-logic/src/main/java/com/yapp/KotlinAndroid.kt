package com.yapp

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * NIA 와 드로디드 나이츠 앱 참고
 * https://github.com/android/nowinandroid/blob/main/build-logic/convention/src/main/kotlin/com/google/samples/apps/nowinandroid/KotlinAndroid.kt
 *
 * 안드로이드 모듈 설정
 */
internal fun Project.configureKotlinAndroid() {
    // Plugins
    with(pluginManager) {
        apply("org.jetbrains.kotlin.android")
        apply("org.jetbrains.kotlin.plugin.serialization")
    }

    // Android settings
    androidExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 28
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17

        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

    }
    configureKotlinCommon(jvmTarget = JvmTarget.JVM_17)
    val libs = extensions.libs
    dependencies {
        "implementation"(libs.findLibrary("kotlinx.serialization.json").get())
    }
}


/**
 * 라이브러리 모듈 설정
 */
internal fun Project.configureKotlin() {
    // Apply common Kotlin settings
    configureKotlinCommon(jvmTarget = JvmTarget.JVM_17)

    // Additional settings for JVM projects
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }
}


/**
 * Kotlin 프로젝트 공통 설정
 */
private fun Project.configureKotlinCommon(jvmTarget: JvmTarget) {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            this.jvmTarget.set(jvmTarget)
            val warningsAsErrors: String? by project
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())
            freeCompilerArgs.set(
                freeCompilerArgs.get() + listOf(
                    "-opt-in=kotlin.RequiresOptIn"
                )
            )
        }
    }
}