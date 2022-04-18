version = "4.0.0"

plugins {
    `java-library`
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation("com.github.cryptomorin:XSeries:8.7.0")
    implementation("de.tr7zw:item-nbt-api:2.9.2")
    api("com.zaxxer:HikariCP:5.0.1")

    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.10.9")
    implementation("org.codemc.worldguardwrapper:worldguardwrapper:1.2.0-SNAPSHOT")

    api(project(":UltimateHelper:common-versionhook"))
    implementation(project(":UltimateHelper:v1_8_R3"))

}

