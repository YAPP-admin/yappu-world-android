import com.yapp.app.setNamespace

plugins {
    id("yapp.android.feature")
}

android {
    setNamespace("feature.home")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)
    implementation(libs.compose.markdown)
}