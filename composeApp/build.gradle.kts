import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.3.10"
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)

            implementation("io.ktor:ktor-client-core:3.4.0")
            implementation("io.ktor:ktor-client-cio:3.4.0")
            implementation("io.ktor:ktor-client-content-negotiation:3.4.0")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.0")

            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.2")

            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "ru.kulishov.application_showcase"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.kulishov.application_showcase"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
    implementation("com.google.dagger:hilt-android:2.58")
    ksp("com.google.dagger:hilt-android-compiler:2.58")
//    ksp("org.jetbrains.kotlin:kotlin-metadata-jvm:2.3.0")
    implementation(libs.androidx.hilt.navigation.compose)
}

