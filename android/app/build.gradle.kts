plugins {
    id("com.android.application")
    kotlin("plugin.serialization")
    kotlin("android")
    id("kotlin-kapt")
    id("com.squareup.anvil")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "so.howl.android"
        minSdk = 24
        targetSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources {
            excludes += "META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
}