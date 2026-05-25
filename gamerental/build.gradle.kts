import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    id("io.fluxzero.tools.gradle.plugin") version "1.1.57"
    id("org.springframework.boot") version "3.5.14"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

val fluxzeroVersion = "1.184.1"
val lombokVersion = "1.18.46"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

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
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    developmentOnly(platform(SpringBootPlugin.BOM_COORDINATES))
    annotationProcessor(platform("io.fluxzero:fluxzero-bom:$fluxzeroVersion"))
    testImplementation(platform("io.fluxzero:fluxzero-bom:$fluxzeroVersion"))
    testImplementation(platform(SpringBootPlugin.BOM_COORDINATES))

    implementation("org.springframework.boot:spring-boot-starter")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("io.fluxzero:sdk")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("io.fluxzero:sdk")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.fluxzero:sdk") {
        artifact {
            classifier = "tests"
        }
    }
    testImplementation("io.fluxzero:test-server")
    testImplementation("io.fluxzero:proxy")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.5.32")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("fluxzero.maven.enabled", "true")
}

tasks.bootJar {
    archiveFileName.set("app.jar")
}

tasks.register<JavaExec>("runTestApp") {
    group = "application"
    description = "Runs the TestApp"
    classpath = sourceSets["test"].runtimeClasspath
    mainClass.set("com.example.app.TestApp")
}
