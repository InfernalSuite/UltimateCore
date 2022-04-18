version = "4.0.0"

dependencies {
    implementation("com.github.cryptomorin:XSeries:8.7.0")
    implementation("de.tr7zw:item-nbt-api:2.9.2")

    implementation(project(":UltimateCrafting:UltimateCraftingNMS"))

    implementation(project(":UltimateCrafting:UltimateCraftingNMS-v1_8_R3"))
    implementation(project(":UltimateCrafting:UltimateCraftingNMS-v1_12_R1"))
    implementation(project(":UltimateCrafting:UltimateCraftingNMS-v1_14_R1"))
    implementation(project(":UltimateCrafting:UltimateCraftingNMS-v1_15_R1"))
    implementation(project(":UltimateCrafting:UltimateCraftingNMS-v1_16_R3"))
    implementation(project(":UltimateCrafting:UltimateCraftingNMS-v1_17_R1"))
    implementation(project(":UltimateCrafting:UltimateCraftingNMS-v1_18_R1"))
    implementation(project(":UltimateCrafting:UltimateCraftingNMS-v1_18_R2"))

    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
}
