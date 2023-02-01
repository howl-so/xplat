enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    plugins {
        val composeVersion = extra["composeVersion"] as String
        id("org.jetbrains.compose").version(composeVersion)

        val kmmBridgeVersion = extra["kmmBridgeVersion"] as String
        id("co.touchlab.faktory.kmmbridge").version(kmmBridgeVersion)

        val kotlinVersion = "1.7.10"
        id("org.jetbrains.kotlin.plugin.parcelize").version(kotlinVersion)
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

include(":android:feature:account")
include(":android:feature:home")
include(":android:feature:swipe")

include(":common:storekit")