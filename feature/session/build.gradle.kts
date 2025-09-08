import com.yapp.app.setNamespace

plugins {
    id("yapp.android.feature")
}

android {
    setNamespace("feature.session")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    implementation(libs.map.sdk)
}
