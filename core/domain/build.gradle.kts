import com.yapp.app.setNamespace

plugins {
    id("yapp.android.library")
}

android {
    setNamespace("core.domain")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.dataApi)

    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.core)
}