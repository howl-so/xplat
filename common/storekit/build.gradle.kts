plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("co.touchlab.faktory.kmmbridge")
    kotlin("plugin.serialization")
    `maven-publish`
    kotlin("native.cocoapods")
    alias(libs.plugins.native.coroutines)
}

kotlin {
    android()
    iosArm64()
    iosX64()
    jvm("desktop")
    js {
        browser()
        binaries.executable()
    }

    cocoapods {
        summary = "StoreKit"
        homepage = "https://github.com/matt-ramotar/howl"
        ios.deploymentTarget = "13"
        version = "0.0.1"

        framework {
            baseName = "StoreKit"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutines.extensions)
                api(libs.store5.snapshot)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.android.driver)
            }
        }

        val nativeMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }

        val desktopMain by getting
    }
}

android {
    namespace = "so.howl.common.storekit"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

addGithubPackagesRepository()
kmmbridge {
    frameworkName.set("StoreKit")
    githubReleaseArtifacts()
    githubReleaseVersions()
    versionPrefix.set("0.0")
    spm()
}