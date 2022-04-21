version = "4.5.0"

dependencies {
    implementation("com.github.cryptomorin:XSeries:8.7.1")
    implementation("de.tr7zw:item-nbt-api:2.9.2")

    implementation(project(":UltimateFarm:UltimateFarmNMS"))
    implementation(project(":UltimateFarm:UltimateFarmNMS-v1_8_R3"))
    implementation(project(":UltimateFarm:UltimateFarmNMS-v1_12_R1"))
    implementation(project(":UltimateFarm:UltimateFarmNMS-v1_14_R1"))
    implementation(project(":UltimateFarm:UltimateFarmNMS-v1_15_R1"))
    implementation(project(":UltimateFarm:UltimateFarmNMS-v1_16_R3"))
    implementation(project(":UltimateFarm:UltimateFarmNMS-v1_17_R1"))
    implementation(project(":UltimateFarm:UltimateFarmNMS-v1_18_R1"))
    implementation(project(":UltimateFarm:UltimateFarmNMS-v1_18_R2"))

    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    implementation(project(":UltimateHelper"))
}
repositories {
    mavenCentral()
}
