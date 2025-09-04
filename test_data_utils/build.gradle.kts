plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.24-1.0.20")
}