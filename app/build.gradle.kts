import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.anilpaudel.newsapp"
    compileSdk = 36
    val properties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }
    defaultConfig {
        applicationId = "com.anilpaudel.newsapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.anilpaudel.newsapp.HiltTestRunner"
    }

    buildTypes {
        buildFeatures.buildConfig = true
        debug {
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org/\"")
            buildConfigField("String", "NEWS_API_KEY", properties.getProperty("news.api.key", ""))
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.bundles.dagger.hilt)
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttp)
    implementation(libs.datastore)
    implementation(libs.ui.test.junit4)
    kapt(libs.dagger.hiltcompiler)
    implementation(libs.bundles.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidxNavigationCompose)
    implementation(libs.kotlinxSerializationJson)
    implementation(libs.kotlinxSerializationJsonConverter)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)
    implementation(libs.bundles.okhttp)
    testImplementation(libs.google.truth)
    androidTestImplementation(libs.google.truth)
    implementation(libs.logcat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.hiltAndroidTesting)
    kaptAndroidTest(libs.dagger.hiltcompiler)
    androidTestImplementation(libs.androidx.compose.junit)
    debugImplementation(libs.androidx.compose.test)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}