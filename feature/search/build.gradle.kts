plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "my.study.search"
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}