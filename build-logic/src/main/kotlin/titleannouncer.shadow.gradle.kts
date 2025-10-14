import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    id("titleannouncer.base")
    id("com.gradleup.shadow")
}

tasks {
    shadowJar {
        archiveBaseName.set("TitleAnnouncer-${project.name.substring(15).uppercaseFirstChar()}")
        archiveClassifier.set("")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        relocate("net.william278.desertwell", "io.github._4drian3d.titleannouncer.libs.desertwell")
        // relocate("org.bstats", "io.github.miniplaceholders.libs.bstats")

        destinationDirectory.set(file("${project.rootDir}/jar"))
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    build {
        dependsOn(shadowJar)
    }
}
