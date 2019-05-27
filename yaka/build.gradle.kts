plugins {
    kotlin("multiplatform")
}

group = "org.jetbrains.yaka"

kotlin {

    jvm()

    sourceSets {
        val commonMain by getting {
            kotlin.srcDirs("src/common")
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            kotlin.srcDirs("src/common-test")
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            kotlin.srcDirs("src/jvm")
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val jvmTest by getting {
            kotlin.srcDirs("src/jvm-test")
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }

}