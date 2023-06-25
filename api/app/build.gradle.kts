plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.datetime")
    this.id("buildSrc.injections")
    this.id("buildSrc.db")
    this.id("buildSrc.serialization")
}

dependencies() {
    this.implementation(this.project(":core"))
    this.implementation("com.samskivert:jmustache:1.15")
    this.implementation("com.twilio.sdk:twilio:9.7.0")
    this.implementation("com.googlecode.libphonenumber:libphonenumber:8.13.6")
}
