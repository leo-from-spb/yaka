buildscript {
    val kotlinVersion by extra("1.8.10")
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    group = "lb.yaka"
    version = version

    repositories {
        mavenCentral()
    }
}
