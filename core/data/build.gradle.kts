plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.dagger)
}

android {
    namespace = "my.study.data"
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(path = ":core:build-config:api"))
    implementation(project(path = ":core:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
}