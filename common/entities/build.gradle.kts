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
}