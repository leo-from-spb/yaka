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
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    modularity.inferModulePath.set(true)
}

sourceSets.main {
    java.srcDirs("module-info", "tests")
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
}
