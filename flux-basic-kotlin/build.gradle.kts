import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.3.21"
    id("org.jetbrains.kotlin.plugin.spring") version "2.3.21"
    id("org.jetbrains.kotlin.kapt") version "2.3.21"
    id("io.fluxzero.tools.gradle.plugin") version "1.1.66"
    id("org.springframework.boot") version "3.5.15"
}

group = "com.example.flux"
version = "1.0-SNAPSHOT"

val fluxzeroVersion = "1.205.0"

repositories {
    mavenCentral()
}

fluxzero {
    projectFiles {
        overrideSdkVersion.set(fluxzeroVersion)
    }
}

dependencies {
    implementation(platform("io.fluxzero:fluxzero-bom:$fluxzeroVersion"))
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.3.21"))
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    developmentOnly(platform(SpringBootPlugin.BOM_COORDINATES))
    kapt(platform("io.fluxzero:fluxzero-bom:$fluxzeroVersion"))
    testImplementation(platform("io.fluxzero:fluxzero-bom:$fluxzeroVersion"))
    testImplementation(platform("org.jetbrains.kotlin:kotlin-bom:2.3.21"))
    testImplementation(platform(SpringBootPlugin.BOM_COORDINATES))

    implementation("org.springframework.boot:spring-boot-starter")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("io.fluxzero:sdk")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    kapt("io.fluxzero:common")

    implementation("io.github.oshai:kotlin-logging-jvm:7.0.14")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.fluxzero:sdk") {
        artifact {
            classifier = "tests"
        }
    }
    testImplementation("io.fluxzero:test-server")
    testImplementation("io.fluxzero:proxy")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("fluxzero.maven.enabled", "true")
}

tasks.bootJar {
    archiveFileName.set("app.jar")
}

kotlin {
    jvmToolchain(25)
}

tasks.register<JavaExec>("runTestApp") {
    group = "application"
    description = "Runs the TestApp"
    classpath = sourceSets["test"].runtimeClasspath
    mainClass.set("com.example.app.TestApp")
}
