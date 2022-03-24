plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("io.papermc.paperweight.userdev") version "1.3.5"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

dependencies {
    compileOnly(project(":titleannouncer-common"))
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
}

val pluginVersion: String = version as String

bukkit {
    main = "me.dreamerzero.titleannouncer.paper.PaperPlugin"
    apiVersion = "1.18"
    website = "https://github.com/4drian3d/TitleAnnouncer"
    authors = listOf("4drian3d")
    version = pluginVersion
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(17)
    }
}