plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.detekt)
}

group = "com.example.flux"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation(libs.spring.boot.starter)

    // Flux
    implementation(libs.fluxzero.sdk)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Monitoring
    implementation(libs.kotlin.logging)

    // Testing
    testImplementation(kotlin("test"))
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.fluxzero.sdk) {
        artifact {
            classifier = "tests"
        }
    }

    // For running the test servers
    testImplementation(libs.fluxzero.test.server)
    testImplementation(libs.fluxzero.proxy)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(
        libs.versions.jdk
            .get()
            .toInt(),
    )
}

tasks.register<JavaExec>("runTestApp") {
    group = "application"
    description = "Runs the TestApp"
    classpath = sourceSets["test"].runtimeClasspath
    mainClass.set("com.example.app.TestApp")
}

