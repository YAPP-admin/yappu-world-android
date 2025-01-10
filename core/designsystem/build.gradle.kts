plugins {
    id("yapp.android.library")
    id("yapp.android.compose")
}

android {
    namespace = "com.yapp.designsystem"
}


dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.graphics)
}
