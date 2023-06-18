plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.serialization")
    this.id("buildSrc.datetime")
    this.id("buildSrc.injections")
}

dependencies {
    this.implementation("io.github.serpro69:kotlin-faker:1.14.0-rc.2")
}
