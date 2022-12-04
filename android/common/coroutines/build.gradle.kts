plugins {
    id("com.android.library")
    kotlin("android")
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
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
