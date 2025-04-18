import com.yapp.app.setNamespace
import java.util.Properties

plugins {
    id("yapp.android.application")
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}
android {
    signingConfigs {
        val keyStoreProperties = Properties()
        keyStoreProperties.load(
            project.rootProject.file("keystore.properties").bufferedReader()
        )
        create("release") {
            storeFile = file(keyStoreProperties["store.file.path"] as String)
            storePassword = keyStoreProperties["store.password"] as String
            keyPassword = keyStoreProperties["key.password"] as String
            keyAlias = keyStoreProperties["key.alias"] as String
        }
    }
    setNamespace("app.official")

    defaultConfig {
        applicationId = "com.yapp.app.official"
        versionCode = 2
        versionName = "1.0.0"

        targetSdk = 35
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }

        create("qa") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("release")
            matchingFallbacks += listOf("release")
        }
    }
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.schedule)
    implementation(projects.feature.notice)
    implementation(projects.feature.profile)
    implementation(projects.feature.signup)
    implementation(projects.feature.login)
    implementation(projects.feature.history)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.core.dataApi)
    implementation(projects.core.domain)
    implementation(projects.core.commonAndroid)
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