
import java.io.FileOutputStream
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("xyz.jpenilla.run-paper") version "1.0.6"

    // TODO:
    /* id("net.minecrell.plugin-yml.bukkit") apply true
    // bukkit {
    //  main = "e"
    //  name = rootProject.name
    //  apiVersion = "1.18"
    //  authors = listOf("authors")
    }*/
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

val bundleJars by tasks.register("bundleJars", DefaultTask::class)

bundleJars.doFirst {
    val zipFile = buildDir.toPath().resolve("jars.zip")

    ZipOutputStream(FileOutputStream(zipFile.toFile())).use { zipOut ->
        childProjects.values.map {
            val path = it.tasks.named<AbstractArchiveTask>("shadowJar").flatMap { shadow -> shadow.archiveFile }.get().asFile.toPath()

            zipOut.putNextEntry(ZipEntry(path.toFile().name))
            Files.copy(path, zipOut)
        }
    }
}

tasks.runServer {
    minecraftVersion("1.19")
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(17))
    })
}

allprojects {

    group = "mc.ultimatecore"

    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenLocal()
        mavenCentral()

        maven("https://libraries.minecraft.net/")
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://repo.rapture.pw/repository/maven-releases/")
        maven("https://repo.glaremasters.me/repository/concuncan/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://maven.enginehub.org/repo/")
    }

    dependencies {
        compileOnly(fileTree("libs").matching {
            include("**/*.jar")
        })
        compileOnly(rootProject.fileTree("libs").matching {
            include("**/*.jar")
        })

        implementation("org.jetbrains:annotations:23.0.0")
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
    }

    tasks.withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
    }

    tasks.withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }

//    val autoRelocate by tasks.register("configureShadowRelocation", com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation::class) {
//        target = tasks.getByName("shadowJar") as ShadowJar?
//        val packageName = "${project.group}.${project.name.toLowerCase()}"
//        prefix = "$packageName.shaded"
//    }

//    tasks.withType<ShadowJar> {
//        dependsOn(autoRelocate)
//    }

    java {
        toolchain {
            toolchain.languageVersion.set(JavaLanguageVersion.of(16))
        }
    }
}

afterEvaluate {
    val moveJars by tasks.register("moveFiles", DefaultTask::class);

    moveJars.doFirst {
        this.project.tasks.runServer {
            pluginJars.setFrom(childProjects.values.map {
                it.tasks.named<AbstractArchiveTask>("shadowJar").flatMap { shadow -> shadow.archiveFile }.get().asFile
            })
        }
    }

    tasks.runServer {
        dependsOn(moveJars)
    }
}
