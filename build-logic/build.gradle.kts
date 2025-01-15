plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}


gradlePlugin {
    plugins {
        register("androidCompose") {
            id = "yapp.android.compose"
            implementationClass = "AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "yapp.android.hilt"
            implementationClass = "AndroidHiltPlugin"
        }
    }
}
