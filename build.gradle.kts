plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

allprojects {
    apply(plugin = "java")
    group = "me.dreamerzero.titleannouncer"
    version = "3.0.0"
    description = "Plugin created to send titles, actionbars, bossbars and chat announces to the player and the proxy using the MiniMessage format"
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://repo.maven.apache.org/maven2/")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveFileName.set("MiniPlaceholders.jar")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        configurations = listOf(project.configurations.shadow.get())
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
