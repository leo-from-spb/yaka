import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm")
    `maven-publish`
}


base.archivesBaseName = "yaka-base"
val moduleName by extra("lb.yaka.base")

val spaceLogin: String by project
val spaceToken: String by project


java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    modularity.inferModulePath.set(true)
    withSourcesJar()
}

sourceSets.main {
    java.srcDirs("module-info", "src")
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
}


tasks {
    compileJava {
        inputs.property("moduleName", moduleName)
        options.compilerArgs = listOf("--patch-module", "$moduleName=${sourceSets.main.get().output.asPath}", "-Xlint:deprecation")
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "lb.yaka"
            artifactId = "lb.yaka.base"
            version = version
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://packages.jetbrains.team/maven/p/dm/maven")
            credentials {
                username = spaceLogin
                password = spaceToken
            }
        }
    }
}
