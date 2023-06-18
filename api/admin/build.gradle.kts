
plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.datetime")
    this.id("buildSrc.injections")
    this.id("buildSrc.serialization")
}

dependencies {
    this.implementation(this.project(":app"))
    this.implementation(this.project(":core"))
}
