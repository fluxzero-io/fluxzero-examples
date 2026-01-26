plugins {
    java
    id("org.springframework.boot") version "3.5.10"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_25

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("io.fluxzero:fluxzero-bom:1.72.0")
    }
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")

    // Fluxzero
    implementation("io.fluxzero:sdk")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("io.fluxzero:sdk")

    //Logback
    testImplementation(enforcedPlatform("ch.qos.logback:logback-core:1.5.26"))

    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.fluxzero:sdk") {
        artifact {
            classifier = "tests"
        }
    }
    testImplementation("io.fluxzero:test-server")
    testImplementation("io.fluxzero:proxy")

    // Lombok for tests
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    archiveFileName.set("app.jar")
}

tasks.register<JavaExec>("runTestApp") {
    group = "application"
    description = "Runs the TestApp"
    classpath = sourceSets["test"].runtimeClasspath
    mainClass.set("com.example.app.TestApp")
}