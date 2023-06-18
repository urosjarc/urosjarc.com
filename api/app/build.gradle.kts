plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.datetime")
    this.id("buildSrc.injections")
    this.id("buildSrc.serialization")
    this.id("io.realm.kotlin") version "1.6.0" apply false
}

dependencies() {
    this.implementation(this.project(":core"))
    this.implementation("org.litote.kmongo:kmongo-serialization:4.8.0")
    this.implementation("io.realm.kotlin:library-base:1.6.0")
    this.implementation("com.samskivert:jmustache:1.15")
    this.implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.1")
    this.implementation("com.twilio.sdk:twilio:9.2.3")
    this.implementation("com.googlecode.libphonenumber:libphonenumber:8.13.6")
}
