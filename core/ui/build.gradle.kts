plugins {
    id("yapp.android.library")
    id("yapp.android.compose")
}

android {
    namespace = "com.yapp.ui"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.androidx.appcompat)
}