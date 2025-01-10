import com.yapp.app.setNamespace

plugins {
    id("yapp.android.application")

    /** Hilt 적용하며 compose 및 android 플러그인 제거 예정 **/
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    setNamespace("app.official")
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:notice"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:login"))


    /** Hilt 적용하며 compose 및 android 라이브러리 제거 예정 **/
    implementation(libs.androidx.core.ktx)
}