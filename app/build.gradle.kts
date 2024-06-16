plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.9.24"
}

android {
    namespace = "com.tao.opensight"
    compileSdk = 34

    viewBinding {
        enable=true
    }

    defaultConfig {
        applicationId = "com.tao.opensight"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:3.3.3")

    implementation(libs.activity.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.fragment.ktx)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.viewpager2)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.common.jvm)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.flycoTabLayout)
    implementation(libs.eventbus)
    implementation(libs.glide)
    implementation(libs.mmkv)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.squareup.okhttp)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.google.gson)
    implementation (libs.converter.kotlinx.serialization)
    implementation (libs.utilcodex)
    implementation (libs.converter.scalars)
    implementation(libs.androidx.paging.runtime.ktx)

}