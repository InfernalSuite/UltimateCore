version = "5.2.8"

plugins {
    `java-library`
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    api(project(":UltimateHelper:common-versionhook"))
    implementation(project(":UltimateHelper:v1_8_R3"))
    implementation(project(":UltimateHelper:v1_12_R1"))
    implementation(project(":UltimateHelper:v1_14_R1"))
    implementation(project(":UltimateHelper:v1_15_R1"))
    implementation(project(":UltimateHelper:v1_16_R3"))
    implementation(project(":UltimateHelper:v1_17_R1"))
    implementation(project(":UltimateHelper:v1_18_R1"))
    implementation(project(":UltimateHelper:v1_18_R2"))
    implementation(project(":UltimateHelper:v1_19_R1"))

    implementation("com.github.cryptomorin:XSeries:8.7.0")
    implementation("de.tr7zw:item-nbt-api:2.10.0")
    api("com.zaxxer:HikariCP:5.0.1")

    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.10.9")
    implementation("org.codemc.worldguardwrapper:worldguardwrapper:1.2.0-SNAPSHOT")
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        relocate("de.tr7zw", "mc.ultimatecore.helper.depends")
    }
}