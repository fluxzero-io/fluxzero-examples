plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.fluxzero.gradle.plugin)
    alias(libs.plugins.spring.boot)
    // TODO: re-enable when detekt 2.0.0 is released (requires JDK 25 support)
    // alias(libs.plugins.detekt)
}

group = "com.example.flux"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.spring.boot.bom))
    implementation(platform(libs.fluxzero.bom))

    implementation(libs.spring.boot.starter)

    implementation(libs.fluxzero.sdk)
    implementation(libs.jackson.module.kotlin)
    kapt(libs.fluxzero.common)

    implementation(libs.kotlin.logging)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.fluxzero.sdk) {
        artifact {
            classifier = "tests"
        }
    }
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
