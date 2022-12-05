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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common:entities"))
            }
        }
    }
}

android {
    namespace = "so.howl.common.api"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()
}