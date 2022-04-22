plugins {
    java
}

group = "mc.ultimatecore"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.10-SNAPSHOT")
    compileOnly(project(":UltimateHelper:common-versionhook"))
}