plugins {
    alias(libs.plugins.pluginyml)
    alias(libs.plugins.runpaper)
}

dependencies {
    implementation(projects.titleannouncerCommon)
    compileOnly(libs.paper)
    implementation(libs.cloud.paper)
}

paper {
    main = "io.github._4drian3d.titleannouncer.paper.PaperPlugin"
    apiVersion = "1.20"
    website = "https://github.com/4drian3d/TitleAnnouncer"
    authors = listOf("4drian3d")
    version = project.version as String
}
