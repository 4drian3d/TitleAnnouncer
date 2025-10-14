plugins {
    id("titleannouncer.base")
    id("com.gradleup.shadow")
}

tasks {
    shadowJar {
        archiveBaseName.set("TitleAnnouncer-${project.capitalizeName}")
        archiveClassifier.set("")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        relocate("net.william278.desertwell", "io.github._4drian3d.titleannouncer.libs.desertwell")
        // relocate("org.bstats", "io.github.miniplaceholders.libs.bstats")

        destinationDirectory.set(file("${project.rootDir}/jar"))
    }
    build {
        dependsOn(shadowJar)
    }
}