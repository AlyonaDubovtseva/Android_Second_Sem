pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "itis-android-uprising-26"
include(":app")
include(":core:data")
include(":core:design")
include(":core:network")
include(":feature:search")
include(":core:build-config:api")
include(":core:di")
include(":core:domain")
include(":core:utils")
include(":core:build-config:impl")
