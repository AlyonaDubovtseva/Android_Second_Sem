plugins {
    alias(libs.plugins.app.android.library)
}

var keyValue = ""
val propsFile = File("properties")
if(propsFile.exists()) {
    keyValue = propsFile.readText()
}

android {
    namespace = "my.study.impl"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "CATS_API_BASE_URL", "\"https://api.thecatapi.com/v1/\"")
        buildConfigField("String", "CATS_API_KEY", "\"$keyValue\"")
    }
}

dependencies {
    implementation(project(":core:build-config:api"))
}