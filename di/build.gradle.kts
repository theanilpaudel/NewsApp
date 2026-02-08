import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.anilpaudel.di"
    compileSdk = 36

    val properties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
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
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(libs.bundles.dagger.hilt)
    kapt(libs.dagger.hiltcompiler)
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)
    implementation(libs.datastore)
    implementation(libs.kotlinxSerializationJson)
    implementation(libs.kotlinxSerializationJsonConverter)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}