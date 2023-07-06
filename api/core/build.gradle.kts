plugins {
    kotlin("multiplatform")
    this.id("buildSrc.domainMap")
}

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {}
        useCommonJs()
    }
    jvm()
}
group = "si.urosjarc"
version = "1.0-SNAPSHOT"

configure<BuildSrc_domainMap_gradle.DomainMapExtension> {
    this.inputDir.set("src/main/kotlin/si/urosjarc/server/core/domain")
}
