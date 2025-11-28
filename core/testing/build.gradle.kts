plugins {
    id("yapp.kotlin.library")
}

dependencies {
    implementation(projects.core.dataApi)
    implementation(projects.core.model)
}
