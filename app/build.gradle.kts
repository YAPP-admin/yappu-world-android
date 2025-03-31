import com.yapp.app.setNamespace

plugins {
    id("yapp.android.application")
    alias(libs.plugins.google.services)
}

android {
    setNamespace("app.official")

    defaultConfig {
        applicationId = "com.yapp.app.official"
        versionCode = 1
        versionName = "0.0.0"

        targetSdk = 35
    }
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:notice"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:login"))

    implementation(project(":core:designsystem"))
    implementation(project(":core:data")) // For di
    implementation(project(":core:data-api"))
    implementation(project(":core:domain"))

    implementation(project(":detekt"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.timber)
}