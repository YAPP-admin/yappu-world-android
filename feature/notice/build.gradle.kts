plugins {
    id("yapp.android.feature")
}

android {
    namespace = "com.yapp.notice"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}