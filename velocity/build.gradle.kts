dependencies {
    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
    implementation(projects.titleannouncerCommon)
    implementation(libs.cloud.velocity)
}
