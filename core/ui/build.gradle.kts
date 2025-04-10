import com.yapp.app.setNamespace

plugins {
    id("yapp.android.library")
    id("yapp.android.compose")
}

android {
    setNamespace("core.ui")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.designsystem)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.compose.markdown)
}