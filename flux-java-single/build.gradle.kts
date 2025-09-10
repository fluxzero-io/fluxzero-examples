plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_24

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("io.fluxzero:fluxzero-bom:0.1199.1")
    }
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    
    // Flux Capacitor
    implementation("io.fluxzero:java-client")
    
    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("io.fluxzero:java-client")
    
    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.fluxzero:java-client") {
        artifact {
            classifier = "tests"
        }
    }
    testImplementation("io.fluxzero:test-server")
    testImplementation("io.fluxzero:proxy")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    archiveFileName.set("app.jar")
}