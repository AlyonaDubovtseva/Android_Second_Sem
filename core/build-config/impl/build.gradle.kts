import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.app.android.library)
}

android {
    namespace = "my.study.impl"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val properties = Properties()
        val propertiesFile = rootProject.file("local.properties")

        val catApiKey = if (propertiesFile.exists()) {
            properties.load(FileInputStream(propertiesFile))
            properties.getProperty("CAT_API_KEY", "")
        } else {
            ""
        }

        buildConfigField("String", "CAT_API_BASE_URL", "\"https://api.thecatapi.com/v1/\"")
        buildConfigField("String", "CAT_API_KEY", "\"$catApiKey\"")
    }
}

dependencies {
    implementation(project(path = ":core:build-config:api"))
}