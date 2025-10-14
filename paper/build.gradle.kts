plugins {
    alias(libs.plugins.pluginyml)
    alias(libs.plugins.runpaper)
    id("titleannouncer.shadow")
}

dependencies {
    implementation(projects.titleannouncerCommon)
    compileOnly(libs.paper)
}

paper {
    main = "io.github._4drian3d.titleannouncer.paper.TitleAnnouncerPaper"
    loader = "io.github._4drian3d.titleannouncer.paper.TitleAnnouncerPaperLoader"
    apiVersion = "1.21"
    website = "https://github.com/4drian3d/TitleAnnouncer"
    authors = listOf("4drian3d")
    version = project.version as String
}
