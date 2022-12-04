@file:Suppress("UnstableApiUsage")

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

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(project(":android:common:coroutines"))
    implementation(project(":android:common:hig"))
    implementation(project(":android:common:navigation"))
    implementation(project(":android:common:scoping"))

    implementation(project(":common:api"))
    implementation(project(":common:entities"))

    implementation(libs.activity.compose)
    implementation(libs.androidx.viewmodel)
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.dagger.dagger)
    kapt(libs.dagger.compiler)
}