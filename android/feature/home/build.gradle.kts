@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
    id("com.squareup.anvil")
    kotlin("plugin.serialization")
}

android {
    compileSdk = 33

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(project(":android:feature:swipe"))
    implementation(project(":common:storekit"))

    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)
    implementation(libs.dagger.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.compose.material3)
}
