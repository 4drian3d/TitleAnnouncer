pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "titleannouncer-parent"

include("titleannouncer-common")
include("titleannouncer-paper")
include("titleannouncer-velocity")

project(":titleannouncer-common").projectDir = file("common")
project(":titleannouncer-velocity").projectDir = file("velocity")
project(":titleannouncer-paper").projectDir = file("paper")