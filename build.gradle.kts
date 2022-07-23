plugins {
    java
    `maven-publish` // repositories\maven에 "-publish" 붙은...
    // id("org.springframework.boot") version "2.4.1"
    // id("org.projectlombok") version "1.18.24"
}

repositories {
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

val springBootVersion = "2.4.1"
val jacksonDatatypeVersion = "2.11.4"
val lombokVersion = "1.18.24"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-configuration-processor:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-security:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:${springBootVersion}")

    implementation("org.apache.commons:commons-lang3:3.11")
    implementation("commons-io:commons-io:2.8.0")

    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonDatatypeVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner:${jacksonDatatypeVersion}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${jacksonDatatypeVersion}")

    implementation("com.auth0:java-jwt:3.12.0")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("com.zaxxer:HikariCP:3.4.5")
    compileOnly("com.h2database:h2:1.4.199") // 수정(implementation → comfileOnly)

    testImplementation("org.springframework.security:spring-security-test:5.4.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}") {
        exclude(group = "com.vaadin.external.google", module = "android-json")
        exclude(group = "junit", module = "junit")
    }

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${springBootVersion}") // JPA
    implementation("org.springframework.boot:spring-boot-starter-tomcat:${springBootVersion}") // Tomcat
    implementation("org.springframework.boot:spring-boot-devtools:${springBootVersion}") // h2database

    implementation("javax.xml.bind:jaxb-api:2.3.0") // Java 9+ 인 경우에는 불필요

    compileOnly("org.projectlombok:lombok:${lombokVersion}") // Lombok
    testCompileOnly("org.projectlombok:lombok:${lombokVersion}") // Lombok
}

group = "com.github.prgrms"
version = "1.0.0-SNAPSHOT"
description = "api-managing-orders"
java.sourceCompatibility = JavaVersion.VERSION_1_8
