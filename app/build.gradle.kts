import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.detekt)

    // Hilt
    // -------------------------------------------
    kotlin("kapt")
    alias(libs.plugins.hilt)

    // Room
    // -------------------------------------------
    alias(libs.plugins.ksp)
}

// Load TMDB API secrets
val tmdbApiSecretsFile = rootProject.file("local/secrets/tmdb_api.properties")
val tmdbApiSecretsProperties = Properties()
tmdbApiSecretsProperties.load(FileInputStream(tmdbApiSecretsFile))

android {
    namespace = "com.rbraithwaite.yourmovielog"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rbraithwaite.yourmovielog"
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

            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
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
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
        checkReleaseBuilds = true

        htmlReport = true
        xmlReport = true
        htmlOutput = file("build/reports/lint/lint-output.html")
        xmlOutput = file("build/reports/lint/lint-output.xml")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    // TODO [23-08-21 11:33p.m.] -- this material dep probably isn't needed since I'm using compose.
    implementation(libs.google.material)

    // Compose
    // -------------------------------------------
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)


    // Compose Extra
    // -------------------------------------------
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose) // for collectAsStateWithLifecycle


    // Navigation
    // -------------------------------------------
    implementation(libs.androidx.navigation.compose)

    // Hilt
    // -------------------------------------------
    implementation(libs.hilt)
    // REFACTOR [23-10-10 3:15p.m.] -- complete migration to KSP
    //  https://developer.android.com/build/migrate-to-ksp.
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.nav.compose)

    // Room
    // -------------------------------------------
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Network
    // -------------------------------------------
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.gson)

    // Misc
    // -------------------------------------------
    implementation(libs.timber)

    // java 8 desugaring
    // TODO [26-01-9 10:03p.m.] why was I desugaring again? try to get rid of this and
    //  upgrade java version.
    coreLibraryDesugaring(libs.desugar.libs)

    detektPlugins(libs.detekt.formatting)


    // Unit tests
    // -------------------------------------------
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.hamcrest)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.robolectric)
    testImplementation(libs.core.testing)

    // TODO [26-01-9 10:07p.m.] get rid of this.
    // Reflection for DelegateFake
    testImplementation(kotlin("reflect"))

    testImplementation(project(":test_data_utils"))
    kspTest(project(":test_data_utils"))

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso)
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom("${rootProject.projectDir}/detekt.yml")
    baseline = file("detekt-baseline.xml")
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
    reports {
        html.required.set(true)
        html.outputLocation.set(file("build/reports/detekt/detekt.html"))
        // TODO [26-01-10 5:22p.m.] later add checkstyle format for CI
    }
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}