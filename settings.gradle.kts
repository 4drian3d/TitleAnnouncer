@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "titleannouncer-parent"

include("titleannouncer-common")
include("titleannouncer-paper")
include("titleannouncer-velocity")

project(":titleannouncer-common").projectDir = file("common")
project(":titleannouncer-velocity").projectDir = file("velocity")
project(":titleannouncer-paper").projectDir = file("paper")