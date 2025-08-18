plugins {
    kotlin("jvm") version "2.2.0"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.thestars.orbit"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.dv8tion:JDA:5.0.0-beta.24")
    implementation("club.minnced:jda-ktx:0.11.0-beta.20")

    implementation("ch.qos.logback:logback-classic:1.5.13")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("org.postgresql:postgresql:42.7.7")
}

application {
    mainClass.set("me.thestars.orbit.OrbitLauncher")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "me.thestars.orbit.OrbitLauncher"
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}