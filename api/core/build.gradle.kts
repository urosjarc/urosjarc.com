plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.serialization")
    this.id("buildSrc.datetime")
    this.id("buildSrc.domainMap")
    this.id("buildSrc.injections")
    this.id("buildSrc.reflection")
    this.id("buildSrc.db")
}

dependencies {
    this.runtimeOnly("org.bouncycastle:bcprov-jdk18on:1.76")
    this.implementation("org.springframework.security:spring-security-core:6.1.2")
    this.implementation("io.github.serpro69:kotlin-faker:1.14.0-rc.2")
    this.implementation("com.samskivert:jmustache:1.15")
    this.implementation("com.twilio.sdk:twilio:9.7.0")
    this.implementation("com.googlecode.libphonenumber:libphonenumber:8.13.6")
}

configure<BuildSrc_domainMap_gradle.DomainMapExtension> {
    this.inputDir.set("src/main/kotlin/core/domain")
}
