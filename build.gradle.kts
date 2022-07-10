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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.4.1")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.4.1")
    implementation("org.springframework.boot:spring-boot-configuration-processor:2.4.1")
    implementation("org.springframework.boot:spring-boot-starter-security:2.4.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.4.1")
    implementation("org.apache.commons:commons-lang3:3.11")
    implementation("commons-io:commons-io:2.8.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.4")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner:2.11.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.11.4")
    implementation("com.auth0:java-jwt:3.12.0")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("com.h2database:h2:1.4.199")
    testImplementation("org.springframework.security:spring-security-test:5.4.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.4.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.4.1") // JPA
    compileOnly("org.projectlombok:lombok:1.18.24") // Lombok
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
