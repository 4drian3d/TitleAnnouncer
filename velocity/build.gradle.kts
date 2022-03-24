dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.2-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.2-SNAPSHOT")
    compileOnly(project(":titleannouncer-common"))
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release.set(17)
}