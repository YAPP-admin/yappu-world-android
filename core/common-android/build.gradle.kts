import com.yapp.app.setNamespace

plugins {
    id("yapp.android.library")
}

android {
    setNamespace("core.core.common.android")
}

dependencies {

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.timber)
}