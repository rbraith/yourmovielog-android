plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Hilt
    // -------------------------------------------
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

    // Room
    // -------------------------------------------
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.rbraithwaite.untitledmovietracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rbraithwaite.untitledmovietracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-pre-alpha"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    // TODO [23-08-21 11:33p.m.] -- this material dep probably isn't needed since I'm using compose.
    implementation("com.google.android.material:material:1.9.0")

    // Compose
    // -------------------------------------------
    val composeBom = platform("androidx.compose:compose-bom:2023.08.00")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")


    // Compose Extra
    // -------------------------------------------
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Navigation
    // -------------------------------------------
    val navVersion = "2.7.1"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Hilt
    // -------------------------------------------
    val hiltVersion = "2.48"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    // REFACTOR [23-10-10 3:15p.m.] -- complete migration to KSP
    //  https://developer.android.com/build/migrate-to-ksp.
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Room
    // -------------------------------------------
    val roomVersion = "2.5.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")


    // Misc
    // -------------------------------------------
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.google.code.gson:gson:2.10.1")

    // java 8 desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}