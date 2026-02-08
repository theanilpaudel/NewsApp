
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.anilpaudel.features"
    compileSdk = 36

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
    implementation(project(":domain"))
    implementation(project(":di"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.bundles.dagger.hilt)
    kapt(libs.dagger.hiltcompiler)
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttp)
    implementation(libs.datastore)
    implementation(libs.ui.test.junit4)
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