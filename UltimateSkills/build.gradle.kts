version = "4.0.0"

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    implementation("com.github.cryptomorin:XSeries:8.7.0")
    implementation("de.tr7zw:item-nbt-api:2.9.2")

    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly("be.maximvdw:MVdWPlaceholderAPI:3.1.1-SNAPSHOT"){
        isTransitive = false
    }
    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly(project(":UltimateHelper"))
    compileOnly(project(":UltimateEnchantment"))
    compileOnly(project(":UltimateCrafting:UltimateCraftingPlugin"))
    compileOnly(project(":UltimateFarm:UltimateFarmPlugin"))
    compileOnly(project(":UltimateAnvil"))
}
