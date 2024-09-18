plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.tutorial.kmpizza.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "dev.tutorial.kmpizza.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.8.20"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    //implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
   // implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)
    //implementation(project(":shared"))

    implementation(Versions.Android.COMPOSE_UI)
    implementation(Versions.Android.COMPOSE_GRAPHICS)
    implementation(Versions.Android.COMPOSE_TOOLING)
    implementation(Versions.Android.COMPOSE_FOUNDATION)
    implementation(Versions.Android.COMPOSE_MATERIAL)
    implementation(Versions.Android.COMPOSE_NAVIGATION)
    implementation(Versions.Android.COMPOSE_ACTIVITY)
    implementation(Versions.Android.MATERIAL_COMPONENTS)
    implementation(libs.compose.material3)
    implementation(Versions.Android.KOIN_ANDROID_MAIN)
}