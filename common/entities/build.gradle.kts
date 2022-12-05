plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()
    js {
        browser()
        nodejs()
    }
    ios()
    jvm()
}

android {
    namespace = "so.howl.common.entities"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}