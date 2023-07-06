import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")

    this.id("buildSrc.domainMap")
}

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            }
        }
    }

    jvm {}
    js(IR) { useCommonJs() }
}
group = "si.urosjarc"
version = "1.0-SNAPSHOT"

configure<BuildSrc_domainMap_gradle.DomainMapExtension> {
    this.inputDir.set("src/main/kotlin/si/urosjarc/server/core/domain")
}
