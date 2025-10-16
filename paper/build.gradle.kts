import net.minecrell.pluginyml.paper.PaperPluginDescription

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
    name = "TitleAnnouncer"
    main = "io.github._4drian3d.titleannouncer.paper.TitleAnnouncerPaper"
    loader = "io.github._4drian3d.titleannouncer.paper.TitleAnnouncerPaperLoader"
    apiVersion = "1.21"
    website = "https://modrinth.com/plugin/titleannouncer"
    authors = listOf("4drian3d")
    version = project.version as String
    serverDependencies {
        register("MiniPlaceholders") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = false
        }
    }
}
