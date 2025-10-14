//plugins {
//    id("titleannouncer.shadow")
//}

dependencies {
    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
    implementation(projects.titleannouncerCommon)
}
