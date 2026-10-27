plugins {
    kotlin("jvm") version "2.3.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.xerial:sqlite-jdbc:3.43.0.0")

}

kotlin {
    jvmToolchain(24)
}

tasks.test {
    useJUnitPlatform()
}