import com.yapp.app.setNamespace

plugins {
    id("yapp.android.library")
    id("yapp.android.compose")
}

android {
    setNamespace("core.ui")
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.appcompat)
}