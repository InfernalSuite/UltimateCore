version = "4.0.0"

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.lucko.me")
}

dependencies {
    implementation("com.github.cryptomorin:XSeries:8.7.0")
    implementation("de.tr7zw:item-nbt-api:2.9.2")
    implementation("me.lucko:helper:5.6.9")
    implementation("com.google.inject:guice:5.1.0")
    implementation(project(":UC-API"))

    api("com.zaxxer:HikariCP:5.0.1")

    api("com.github.ben-manes.caffeine:caffeine:3.0.6")

    api("com.google.code.gson:gson:2.9.0")
    api("com.google.guava:guava:31.1-jre")

    api("org.spongepowered:configurate-core:4.1.2") {
        isTransitive = false
    }
    api("org.spongepowered:configurate-yaml:4.1.2") {
        isTransitive = false
    }
    api("org.spongepowered:configurate-gson:4.1.2") {
        isTransitive = false
    }
    api("org.spongepowered:configurate-hocon:4.1.2") {
        isTransitive = false
    }
    api("me.lucko.configurate:configurate-toml:3.7") {
        isTransitive = false
    }

    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly("org.codemc.worldguardwrapper:worldguardwrapper:1.2.0-SNAPSHOT")
}
