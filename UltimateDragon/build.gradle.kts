version = "4.0.0"

dependencies {
    implementation("com.github.cryptomorin:XSeries:8.7.0")
    implementation("de.tr7zw:item-nbt-api:2.9.2")

    implementation(project(":UltimateDragon:UltimateDragonNMS"))
    implementation(project(":UltimateDragon:UltimateDragonNMS-v1_8_R3"))
    implementation(project(":UltimateDragon:UltimateDragonNMS-v1_12_R1"))
    implementation(project(":UltimateDragon:UltimateDragonNMS-v1_14_R1"))
    implementation(project(":UltimateDragon:UltimateDragonNMS-v1_15_R1"))
    implementation(project(":UltimateDragon:UltimateDragonNMS-v1_16_R3"))
    implementation(project(":UltimateDragon:UltimateDragonNMS-v1_17_R1"))
    implementation(project(":UltimateDragon:UltimateDragonNMS-v1_18_R1"))
    implementation(project(":UltimateDragon:UltimateDragonNMS-v1_18_R2"))

    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")

    compileOnly(project(":UltimateHelper"))
}
