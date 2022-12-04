enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    plugins {
        val composeVersion = extra["composeVersion"] as String
        id("org.jetbrains.compose").version(composeVersion)
    }

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}

rootProject.name = "howl"

include(":android:app")
include(":android:common:scoping")
include(":android:common:coroutines")
include(":android:common:navigation")
include(":android:common:hig")

include(":common:api")
include(":common:entities")