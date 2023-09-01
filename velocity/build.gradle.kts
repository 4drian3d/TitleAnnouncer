dependencies {
    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
    compileOnly(projects.titleannouncerCommon)
    implementation(libs.cloud.velocity)
}
