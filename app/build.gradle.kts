plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // ADDED: The required plugin for 'kapt' must be defined here at the top level
    id("kotlin-kapt")
}

android {
    namespace = "com.example.pokedex2"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.pokedex2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Existing dependencies using 'libs' aliases
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Explicit implementation dependencies (cleaned up duplicates)
    implementation("androidx.core:core-ktx:1.12.0") // Note: Redundant if using libs.androidx.core.ktx
    implementation("androidx.appcompat:appcompat:1.6.1") // Note: Redundant if using libs.androidx.appcompat
    implementation("com.google.android.material:material:1.10.0") // Note: Redundant if using libs.material

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Glide with Kapt
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0") // Kapt is now resolved

    // For Downloadable Fonts
    implementation("androidx.core:core:1.12.0")

    // REMOVED the misplaced plugins {} block that caused the error.
}