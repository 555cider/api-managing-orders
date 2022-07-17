plugins {
    java
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

val springBootVersion = "2.4.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-configuration-processor:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-security:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:${springBootVersion}")
    implementation("org.apache.commons:commons-lang3:3.11")
    implementation("commons-io:commons-io:2.8.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.4")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner:2.11.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.11.4")
    implementation("com.auth0:java-jwt:3.12.0")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("com.zaxxer:HikariCP:3.4.5")
    testImplementation("org.springframework.security:spring-security-test:5.4.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}")

    runtimeOnly("com.h2database:h2:1.4.199") // 수정(implementation → runtimeOnly)

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}") // JPA
    implementation("org.springframework.boot:spring-boot-starter-tomcat:${springBootVersion}") // Tomcat
    implementation("org.springframework.boot:spring-boot-devtools:${springBootVersion}") // h2database

    implementation("javax.xml.bind:jaxb-api:2.3.0") // Java 9+ 인 경우에는 불필요

    annotationProcessor("org.projectlombok:lombok:1.18.24") // Lombok
    compileOnly ("org.projectlombok:lombok:1.18.24") // Lombok
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24") // Lombok
    testCompileOnly("org.projectlombok:lombok:1.18.24") // Lombok
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

group = "com.github.prgrms"
version = "1.0.0"
description = "api-managing-orders"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
