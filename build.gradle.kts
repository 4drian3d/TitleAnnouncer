import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    java
    alias(libs.plugins.shadow)
}

subprojects {
    apply<JavaPlugin>()
    apply<ShadowPlugin>()

    val projectName = project.name.replace("titleannouncer-", "").uppercaseFirstChar()

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

    tasks {
        withType<JavaCompile> {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(21)
        }
        if (projectName != "Common") {
            withType<ShadowJar> {
                archiveFileName.set("TitleAnnouncer-${projectName}-${project.version}.jar")
                archiveClassifier.set("")
                doLast {
                    copy {
                        from(archiveFile)
                        into("${rootProject.projectDir}/build")
                    }
                }
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            }
        }
        build {
            dependsOn(shadowJar)
        }
    }
}
