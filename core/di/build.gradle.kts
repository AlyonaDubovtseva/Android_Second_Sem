plugins {
    alias(libs.plugins.app.android.library)
}

android {
    namespace = "my.study.di"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation(project(":core:build-config:api"))
    implementation(project(":core:build-config:impl"))
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:utils"))

}