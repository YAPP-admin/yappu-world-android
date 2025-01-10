plugins {
    id("yapp.android.library")
}

android{
    namespace = "com.yapp.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data-api"))

    implementation(libs.androidx.core.ktx)
}