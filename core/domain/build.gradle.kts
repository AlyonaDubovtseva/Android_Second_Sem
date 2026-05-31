plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.dagger)
}

android {
    namespace = "my.study.domain"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)


}