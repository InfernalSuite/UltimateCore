plugins {
    java
}

group = "mc.ultimatecore"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.10-SNAPSHOT")
    compileOnly(project(":UltimateHelper:common-versionhook"))
}

java {
    toolchain {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}