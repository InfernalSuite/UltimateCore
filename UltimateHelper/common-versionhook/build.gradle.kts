plugins {
    java
}

group = "mc.ultimatecore"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.13.1-R0.1-SNAPSHOT")
}