import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.app.android.library)
}

android {
    namespace = "my.study.network"
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val apiKey = try {
            val propertiesFile = project.rootProject.file("local.properties")
            if(propertiesFile.exists()) {
                val properties = Properties()
                properties.load(FileInputStream(propertiesFile))
                properties.getProperty("GENIUS_API_KEY") ?: "\"\""
            } else {
                println(" local.properties not found! Using empty API key.")
                "\"\""
            }
        } catch (e: Exception) {
            println(e.message)
        }

        buildConfigField("String", "GENIUS_API_KEY", apiKey as String)
    }




}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation(project(":core:build-config:api"))
}