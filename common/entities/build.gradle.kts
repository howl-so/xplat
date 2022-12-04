plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()
    js {
        browser()
    }
    ios()
}

android {
    namespace = "so.howl.common.entities"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()
}