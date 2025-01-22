import com.yapp.app.setNamespace

plugins {
    id("yapp.android.library")
    id("yapp.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android{
    setNamespace("core.data")
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data-api"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.timber)
}