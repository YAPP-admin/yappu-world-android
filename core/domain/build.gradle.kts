import com.yapp.app.setNamespace

plugins {
    id("yapp.android.library")
}

android {
    setNamespace("core.domain")
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data-api"))

    implementation(libs.androidx.core.ktx)
}