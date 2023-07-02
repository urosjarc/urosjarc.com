plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.datetime")
    this.id("buildSrc.injections")
    this.id("buildSrc.serialization")
    this.id("buildSrc.domainMap")
    this.id("buildSrc.db")
}

dependencies {
    this.implementation("io.github.serpro69:kotlin-faker:1.14.0-rc.2")
    this.implementation("com.samskivert:jmustache:1.15")
    this.implementation("com.twilio.sdk:twilio:9.7.0")
    this.implementation("com.googlecode.libphonenumber:libphonenumber:8.13.6")
}
// Configure the extension using a DSL block
configure<BuildSrc_domainMap_gradle.DomainMapExtension> {
    this.inputDir.set("src/main/kotlin/si/urosjarc/server/core/domain")
}
