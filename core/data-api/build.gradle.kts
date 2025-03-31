plugins {
    id("yapp.kotlin.library")
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.coroutines.core)
}
