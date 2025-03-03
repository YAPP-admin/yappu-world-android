import com.yapp.configureHiltAndroid

plugins {
    id("yapp.android.library")
    id("yapp.android.compose")
}

android {
    packaging {
        resources {
            excludes.add("META-INF/**")
        }
    }
}

configureHiltAndroid()

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:data-api"))
}