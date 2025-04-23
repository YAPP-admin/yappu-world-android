import com.yapp.app.setNamespace

plugins {
    id("yapp.android.feature")
}

android {
    setNamespace("feature.notice")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.compose.markdown)
}