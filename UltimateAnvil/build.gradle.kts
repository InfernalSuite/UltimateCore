version = "5.2.4"

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation("com.github.cryptomorin:XSeries:8.7.0")
    implementation("de.tr7zw:item-nbt-api:2.9.2")
    implementation("net.wesjd:anvilgui:1.5.3-SNAPSHOT")

    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("com.github.Archy-X:AureliumSkills:Beta1.1.3")
    compileOnly(project(":UltimateEnchantment"))
}

