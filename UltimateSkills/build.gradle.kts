import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

version = "5.2.8"

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    implementation("com.github.cryptomorin:XSeries:8.7.0")
    implementation("de.tr7zw:item-nbt-api:2.10.0")
    implementation("com.zaxxer:HikariCP:5.0.1")

    compileOnly("io.lumine:MythicLib:1.3.1")
    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.10.9")
    implementation(project(":UltimateHelper"))
    compileOnly(project(":UltimateEnchantment"))
    compileOnly(project(":UltimateCrafting"))
    compileOnly(project(":UltimateFarm"))
    compileOnly(project(":UltimateAnvil"))
}

tasks {
    named<ShadowJar>("shadowJar") {
        relocate("de.tr7zw", "mc.ultimatecore.skills.depends")
    }
}