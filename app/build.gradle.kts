plugins {
    alias(libs.plugins.app.android.application)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.dagger)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
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
    implementation(libs.navigation.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
}