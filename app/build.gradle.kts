plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.biodex"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.biodex"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://65...mockapi.io/api/v1/\"")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    configurations.all {
        exclude(group = "com.intellij", module = "annotations")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navigation Component
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Hilt (Inyección de Dependencias)
    implementation(libs.hilt.android)
    implementation(libs.moshi.kotlin)
    ksp(libs.hilt.compiler)

    // Utils
    implementation(libs.timber)
    implementation(libs.coil)

    // Retrofit (Red/API)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp.logging)

    // Room (Base de Datos Local)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

}