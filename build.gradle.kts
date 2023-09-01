plugins {
    java
    alias(libs.plugins.shadow)
}

allprojects {
    apply<JavaPlugin>()
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
}

tasks {
    shadowJar {
        archiveFileName.set("TitleAnnouncer-${project.version}.jar")
        archiveClassifier.set("")
        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    build {
        dependsOn(shadowJar)
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
