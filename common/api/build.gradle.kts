plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("co.touchlab.faktory.kmmbridge")
    `maven-publish`
    kotlin("native.cocoapods")
    alias(libs.plugins.native.coroutines)
}

kotlin {
    android()
    js {
        browser()
        nodejs()
    }
    ios()
    jvm()

    cocoapods {
        summary = "HowlApi"
        homepage = "https://github.com/matt-ramotar/howl"
        ios.deploymentTarget = "13"
        version = "0.0.1"

        framework {
            baseName = "HowlApi"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common:entities"))
                implementation(libs.ktor.client.core)


                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}

android {
    namespace = "so.howl.common.api"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

addGithubPackagesRepository()
kmmbridge {
    frameworkName.set("HowlApi")
    githubReleaseArtifacts()
    githubReleaseVersions()
    versionPrefix.set("0.0")
    spm()
}