plugins {
    id("yapp.kotlin.library")
}

dependencies {
    api(projects.core.dataApi)
    api(projects.core.model)
}
