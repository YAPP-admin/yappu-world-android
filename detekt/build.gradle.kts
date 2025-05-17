import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.detekt)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    buildUponDefaultConfig = true
    allRules = false
    ignoreFailures = true
    config.setFrom(file("$rootDir/detekt/src/main/resources/config/detekt-config.yml"))
}

tasks.withType<Detekt> {
    jvmTarget = "17"
    reports {
        html.required.set(false)
        xml.required.set(true)
        xml.outputLocation.set(file("$rootDir/build/reports/detekt/detekt.xml"))
        sarif.required.set(false)
        md.required.set(false)
    }
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "17"
}

dependencies {
    compileOnly(libs.detekt)
}

