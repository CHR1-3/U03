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
    implementation("org.postgresql:postgresql:42.7.4")

}

kotlin {
    jvmToolchain(24)
}

tasks.test {
    useJUnitPlatform()
}