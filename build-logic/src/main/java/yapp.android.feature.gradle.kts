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

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))

}