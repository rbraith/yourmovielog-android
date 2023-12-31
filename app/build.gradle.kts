import java.io.FileInputStream
import java.util.Properties

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

// Load TMDB API secrets
val tmdbApiSecretsFile = rootProject.file("local/secrets/tmdb_api.properties")
val tmdbApiSecretsProperties = Properties()
tmdbApiSecretsProperties.load(FileInputStream(tmdbApiSecretsFile))

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

        // Create BuildConfig fields
        buildConfigField(
            "String",
            "TMDB_API_BEARER_TOKEN",
            tmdbApiSecretsProperties["TMDB_API_BEARER_TOKEN"] as String
        )
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
        buildConfig = true
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
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2") // for collectAsStateWithLifecycle


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

    // Network
    // -------------------------------------------
    // retrofit
    val retrofitVersion = "2.9.0"
    val okHttpVersion = "4.2.1"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-scalars:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")


    // Misc
    // -------------------------------------------
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.google.code.gson:gson:2.10.1")

    // java 8 desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")


    // Unit tests
    // -------------------------------------------
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    // TODO [23-10-12 10:32p.m.] -- mockito-kotlin latest requires java 11 support.
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okHttpVersion")

    testImplementation(project(":test_data_utils"))
    kspTest(project(":test_data_utils"))

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}