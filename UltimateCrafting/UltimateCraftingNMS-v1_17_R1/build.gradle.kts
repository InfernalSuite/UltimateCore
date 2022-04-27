dependencies {
    compileOnly("org.spigotmc:spigot:1.17-R0.1-SNAPSHOT")
    compileOnly(project(":UltimateCrafting:UltimateCraftingNMS"))
}

java {
    toolchain {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}
