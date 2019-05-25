plugins {
    kotlin("multiplatform")
}


kotlin {

    sourceSets {
        val commonMain by getting {
            kotlin.srcDirs("src")
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
    }

}