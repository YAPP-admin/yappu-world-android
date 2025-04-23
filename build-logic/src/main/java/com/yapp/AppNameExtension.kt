package com.yapp.app

import com.yapp.androidExtension
import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.yapp.$name"
    }
}