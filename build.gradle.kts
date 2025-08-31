plugins {
    kotlin("jvm") version "2.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jgrapht:jgrapht-io:1.5.2")
    implementation("org.jgrapht:jgrapht-core:1.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.0-M2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}