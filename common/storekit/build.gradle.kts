plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("co.touchlab.faktory.kmmbridge")
    kotlin("plugin.serialization")
    `maven-publish`
    kotlin("native.cocoapods")
    alias(libs.plugins.native.coroutines)
    id("com.squareup.sqldelight")
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
        
        val desktopMain by getting {
            dependencies {
                implementation(libs.sqldelight.sqlite.driver)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.sqldelight.sqljs.driver)
            }
        }
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

sqldelight {
    database("HowlDatabase") {
        packageName = "so.howl.common.storekit"
    }
}
