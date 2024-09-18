plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
}

kotlin {
    android()
    jvm()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            // Ktor
            implementation(Versions.Common.KTOR_CLIENT_CORE)
            implementation(Versions.Common.KTOR_LOGGING)
            implementation(Versions.Common.KTOR_CLIENT_JSON)
            implementation(Versions.Common.KTOR_CLIENT_SERIALIZATION)

            api(Versions.Common.KOIN_CORE)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(Versions.Android.KTOR_CLIENT)
            implementation(Versions.Android.KTOR_OKHTTP)
            implementation(Versions.Android.VIEW_MODEL)
        }

    }
}

android {
    namespace = "dev.tutorial.kmpizza"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}
