import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm")
}


base.archivesBaseName = "yaka-test"
val moduleName by extra("yaka.test")

dependencies {
    implementation(project(":yaka.base"))

    implementation("org.jetbrains.kotlin", "kotlin-stdlib", "1.8.10")
    implementation("org.jetbrains.kotlin", "kotlin-reflect", "1.8.10")
    implementation("org.junit.jupiter", "junit-jupiter-api", "5.9.1")
    implementation("org.junit.jupiter", "junit-jupiter-params", "5.9.1")

    implementation("org.junit.jupiter", "junit-jupiter-engine", "5.9.1")
    implementation("org.junit.platform", "junit-platform-launcher", "1.9.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    modularity.inferModulePath.set(true)
}

sourceSets.main {
    java.srcDirs("module-info", "runner", "tests")
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}


tasks {
    compileJava {
        inputs.property("moduleName", moduleName)
        options.compilerArgs = listOf("--patch-module", "$moduleName=${sourceSets.main.get().output.asPath}",
                                      "-Xlint:deprecation")
    }
    jar {
        manifest {
            attributes["Manifest-Version"] = "1.0"
            attributes["Main-Class"] = "lb.yaka.test.launcher.TestLaunch"
            manifest.attributes["Class-Path"] = configurations
                .runtimeClasspath
                .get()
                .joinToString(separator = " ") { file ->
                    file.name
                }
        }
    }

    register("jars", Copy::class) {
        dependsOn(":yaka.base:jar")
        dependsOn(":yaka.test:jar")
        mustRunAfter(":yaka.base:jar")
        mustRunAfter(":yaka.test:jar")

        from("$projectDir/build/libs") {
            include("yaka-test-*.jar")
        }

        configurations
            .runtimeClasspath
            .get()
            .forEach { file ->
                val p = file.absolutePath
                from(p)
                //println(p)
            }
        into("$projectDir/../jars")
    }
}
