plugins {
    alias(libs.plugins.app.android.application)
    alias(libs.plugins.app.compose)
}
android {
    namespace = "ru.itis.android.uprising26"

    defaultConfig {
        applicationId = "ru.itis.android.uprising26"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":feature:search"))
    implementation(project(":core:di"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.x.lifecycle.runtime.ktx)
    implementation(libs.x.activity.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
}