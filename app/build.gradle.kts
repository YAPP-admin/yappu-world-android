import com.yapp.app.setNamespace

plugins {
    id("yapp.android.application")
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}
android {
    setNamespace("app.official")

    defaultConfig {
        applicationId = "com.yapp.app.official"
        versionCode = 2
        versionName = "1.0.0"

        targetSdk = 35
    }
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.notice)
    implementation(projects.feature.signup)
    implementation(projects.feature.login)
    implementation(projects.core.designsystem)
    implementation(projects.core.data) // For di
    implementation(projects.core.dataApi)
    implementation(projects.core.domain)
    implementation(projects.detekt)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.crashlytics)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.splashscreen)
    implementation(libs.timber)
}