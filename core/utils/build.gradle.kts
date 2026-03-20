plugins {
    alias(libs.plugins.app.android.library)
}

android {
    namespace = "my.study.utils"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}