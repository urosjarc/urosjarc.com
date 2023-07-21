plugins {
    this.`kotlin-dsl`
}
repositories {
    this.gradlePluginPortal() // so that external plugins can be resolved in dependencies section
}
dependencies {
    this.implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    this.implementation("org.jetbrains.kotlin:kotlin-serialization:1.7.10")
    this.implementation("org.jetbrains.kotlinx:kover:0.6.1")
}
