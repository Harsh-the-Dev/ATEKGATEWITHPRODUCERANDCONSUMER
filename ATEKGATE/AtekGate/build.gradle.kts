import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
}

group = "com.atek.gate"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {

    // Kotlin Multiplatform
    implementation(compose.desktop.currentOs)

    // Retrofit & Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

    // Event Bus
    implementation("org.greenrobot:eventbus-java:3.3.1")

    // SQLite
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")

    // Sl4J
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.30")

    // Pi4j (Do no update)
    implementation("com.pi4j:pi4j-core:1.4")

    //kafka(Don't update may cause problem)
    implementation("org.apache.kafka:kafka_2.13:2.6.0")

    //databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "AtekGate"
            packageVersion = "1.0.0"
        }
    }
}