plugins {
    `java-library`
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.configurate)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimesssage)
    // TODO: a
    compileOnly(libs.adventure.configurate)
    compileOnly(libs.miniplaceholders)

    compileOnlyApi(libs.guice)
    compileOnly(libs.slf4j)
    compileOnly(libs.brigadier)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    compileJava {
        options.compilerArgs.add("-parameters")
    }
    jar {
        manifest {
            attributes("Automatic-Module-Name" to "io.github._4drian3d.titleannouncer.common")
        }
    }
}