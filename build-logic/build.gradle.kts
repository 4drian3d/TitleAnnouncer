plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.plugin.shadow)
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}