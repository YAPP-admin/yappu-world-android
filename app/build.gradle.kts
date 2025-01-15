import com.yapp.app.setNamespace

plugins {
    id("yapp.android.application")
}

android {
    setNamespace("app.official")
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:notice"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:login"))

}