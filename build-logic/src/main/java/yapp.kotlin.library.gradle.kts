import com.yapp.configureKotlin
import com.yapp.configureTestDependencies

plugins {
    kotlin("jvm")
}

configureKotlin()

if (project.path == ":core:testing") {
    configureTestDependencies(configurationName = "implementation")
} else {
    configureTestDependencies()
}
