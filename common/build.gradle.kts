plugins {
    `java-library`
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.configurate)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimesssage)
    // TODO: a
    compileOnly("net.kyori:adventure-serializer-configurate4:4.11.0")
    compileOnly(libs.miniplaceholders)

    api(libs.cloud.core)
    compileOnlyApi(libs.guice)
    compileOnly(libs.slf4j)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}