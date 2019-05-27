
plugins {
    kotlin("jvm")
}


dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(project(":yaka"))
    testImplementation("junit:junit:4.12")
}