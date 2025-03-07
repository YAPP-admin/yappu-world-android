pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "YappOfficial"
include(":app")

// core
include(
    ":core:model",
    ":core:data-api",
    ":core:designsystem",
    ":core:ui",
    ":core:data",
    ":core:domain"
)

// feature
include(
    ":feature:home",
    ":feature:notice",
    ":feature:signup",
    ":feature:login"
)
