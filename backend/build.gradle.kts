import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

application {
    mainClass.set("dev.tutorial.kmpizza.backend.ServerKt")
}

plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version Versions.SHADOW_JAR_VERSION
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
}

dependencies {
    implementation(kotlin("stdlib"))
    // Ktor
    implementation(Versions.Jvm.KTOR_CLIENT_APACHE)
    implementation(Versions.Jvm.KTOR_SERIALIZATION)
    implementation(Versions.Jvm.KTOR_SERVER_NETTY)
    implementation(Versions.Jvm.KTOR_AUTH)
    implementation(Versions.Jvm.KTOR_WEB_SOCKETS)
    implementation(Versions.Jvm.KTOR_CLIENT_APACHE)
    implementation(libs.slf4j.simple)

    implementation(Versions.Jvm.JETBRAINS_EXPOSED_CORE)
    implementation(Versions.Jvm.JETBRAINS_EXPOSED_DAO)
    implementation(Versions.Jvm.JETBRAINS_EXPOSED_JDBC)

    implementation(Versions.Jvm.POSTGRESQL)
    implementation(Versions.Jvm.HIKARI_CONNECTION_POOL)

    implementation(Versions.Jvm.KOIN_KTOR)

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation(project(":shared"))

    /*classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}")
    classpath("com.android.tools.build:gradle:7.1.0-alpha10")*/
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("Backend")
    archiveClassifier.set("")
    archiveVersion.set("")
    isZip64 = true
}

tasks.named<CreateStartScripts>("startScripts") {
    dependsOn(tasks.named("shadowJar"))
    // Point startScripts to the shadow JAR instead of the default JAR
    doFirst {
        classpath = files(tasks.named<ShadowJar>("shadowJar").get().archiveFile)
    }
}

tasks.named("startShadowScripts").configure {
    enabled = false
}

tasks.named("shadowDistTar") {
    dependsOn(tasks.named("jar"))
}

tasks.named("shadowDistZip") {
    dependsOn(tasks.named("jar"))
}


