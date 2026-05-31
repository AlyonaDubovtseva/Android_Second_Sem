plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.dagger)
}

android {
    namespace = "my.study.search"
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation("androidx.compose.material:material-icons-extended:1.6.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.x.lifecycle.runtime.ktx)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)



}