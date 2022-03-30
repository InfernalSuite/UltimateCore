plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("com.sedmelluq.mass-relocator") version "1.0.0" apply false
}

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("io.freefair.gradle:lombok-plugin:6.3.0")
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}

allprojects {

    group = "mc.ultimatecore"

    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenLocal()
        mavenCentral()

        maven("https://repo.codemc.io/repository/nms/")
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://repo.rapture.pw/repository/maven-releases/")
        maven("https://repo.glaremasters.me/repository/concuncan/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
    }


    dependencies {
        compileOnly(fileTree("libs").matching {
            include("**/*.jar")
        })
        implementation("org.jetbrains:annotations:23.0.0")
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
    }

    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }

    val autoRelocate by tasks.register("configureShadowRelocation", com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation::class) {
        target = tasks.getByName("shadowJar") as com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar?
        val packageName = "${project.group}.${project.name.toLowerCase()}"
        prefix = "$packageName.shaded"
    }

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        dependsOn(autoRelocate)
    }

    java {
        toolchain {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}