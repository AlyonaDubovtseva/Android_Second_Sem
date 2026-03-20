plugins {
    alias(libs.plugins.app.android.library)
}

android {
    namespace = "my.study.data"
}

dependencies {
    implementation(project(":core:network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}