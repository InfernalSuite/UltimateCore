rootProject.name = "ultimatecore"

fileTree(".") {
    include("**/build.gradle.kts")
    exclude("buildSrc/**")
    exclude("build.gradle.kts")
}.map {
    relativePath(it.parent)
        .replace(File.separator, ":")
}.forEach {
    include(it)
}