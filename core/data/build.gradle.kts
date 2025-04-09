import com.yapp.app.setNamespace
import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("yapp.android.library")
    id("yapp.android.hilt")
    alias(libs.plugins.protobuf)
}

android {
    setNamespace("core.data")

    buildFeatures {
        buildConfig = true
    }
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

// https://stackoverflow.com/questions/78634960/ksp-error-when-upgrading-android-project-to-compose-compiler-2-0-0
androidComponents {
    onVariants(selector().all()) { variant ->
        afterEvaluate {
            val capName = variant.name.capitalized()
            tasks.getByName<KotlinCompile>("ksp${capName}Kotlin") {
                setSource(tasks.getByName("generate${capName}Proto").outputs)
            }
        }
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.dataApi)

    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.protobuf.kotlin.lite)

    ksp(libs.encrypted.datastore.preference.ksp)
    implementation(libs.encrypted.datastore.preference.ksp.annotations)
    implementation(libs.encrypted.datastore.preference.security)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.coroutines.play.services)

    implementation(libs.androidx.core.ktx)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.timber)
}