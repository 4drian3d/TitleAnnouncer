import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation

plugins {
    java
	id("net.kyori.blossom") version "1.2.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

repositories {
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    maven {
        url = uri("https://nexus.velocitypowered.com/repository/maven-public/")
    }

    maven {
        url = uri("https://jitpack.io")
    }

    maven {
        url = uri("https://mvn.exceptionflug.de/repository/exceptionflug-public/")
    }
}

dependencies {
    shadow("com.github.simplix-softworks:simplixstorage:3.2.3")
    shadow("net.kyori:adventure-text-minimessage:4.2.0-SNAPSHOT"){
        exclude("net.kyori", "adventure-api")
    }

    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.10.10")
	compileOnly("dev.simplix:protocolize-api:2.0.0")

    compileOnly("com.velocitypowered:velocity-api:3.0.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.0.1")
}

group = "net.dreamerzero.titleannouncer"
version = "2.2.1"
description = "Plugin created to send titles, actionbars, bossbars and chat announces to the player and the proxy using the MiniMessage format."
java.sourceCompatibility = JavaVersion.VERSION_16

blossom{
	val constants = "src/main/java/net/dreamerzero/titleannouncer/common/utils/Constants.java"
	replaceToken("{name}", rootProject.name, constants)
	replaceToken("{version}", version, constants)
	replaceToken("{description}", description, constants)
    replaceToken("{url}", "https://polymart.org/resource/titleannouncer.1350", constants)
}

tasks {
    processResources {
        expand(
            "name" to rootProject.name,
            "version" to version,
            "description" to "Plugin created to send titles, actionbars and bossbars to the player and the proxy using the MiniMessage format.",
            "url" to "https://polymart.org/resource/titleannouncer.1350"
        )
    }
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        dependsOn(getByName("relocateJars") as ConfigureShadowRelocation)
        minimize()
        archiveFileName.set("TitleAnnouncer.jar")
        configurations = listOf(project.configurations.shadow.get())
    }

    create<ConfigureShadowRelocation>("relocateJars") {
        target = shadowJar.get()
        prefix = "net.dreamerzero.titleannouncer.libs"
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
