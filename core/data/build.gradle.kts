import com.yapp.app.setNamespace

plugins {
    id("yapp.android.library")
    id("yapp.android.hilt")
}

android{
    setNamespace("core.data")
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data-api"))

    implementation(libs.androidx.core.ktx)
}