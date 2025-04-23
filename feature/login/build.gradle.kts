import com.yapp.app.setNamespace

plugins {
    id("yapp.android.feature")
    id("yapp.android.hilt")
}

android {
    setNamespace("feature.login")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}