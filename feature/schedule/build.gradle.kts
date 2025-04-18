import com.yapp.app.setNamespace

plugins {
    id("yapp.android.feature")
}

android {
    setNamespace("feature.schedule")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)
}