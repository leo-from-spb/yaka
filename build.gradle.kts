import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
    `java-library`
}

repositories {
    mavenCentral()
    //jcenter()
}


group = "lb.yaka"


kotlin {

    sourceSets {
        val main by getting {
            kotlin.srcDirs("yaka/src")
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))
            }
        }
        val test by getting {
            kotlin.srcDirs("yaka/tests")
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter:5.5.1")
            }
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        //kotlinOptions.suppressWarnings = true
    }

}

buildDir = File("out/gradle")
//libsDirName = File("out/jars")

tasks.withType<Test> {
    useJUnitPlatform()
    //testLogging {
    //    events("passed", "skipped", "failed")
    //}
}