import org.gradle.kotlin.dsl.support.listFilesOrdered

plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.serialization")
    this.id("buildSrc.datetime")
    this.id("buildSrc.domainMap")
    this.id("buildSrc.injections")
    this.id("buildSrc.db")
}

dependencies {
    this.implementation("io.github.serpro69:kotlin-faker:1.14.0-rc.2")
    this.implementation("com.samskivert:jmustache:1.15")
    this.implementation("com.twilio.sdk:twilio:9.7.0")
    this.implementation("com.googlecode.libphonenumber:libphonenumber:8.13.6")
}

configure<BuildSrc_domainMap_gradle.DomainMapExtension> {
    this.inputDir.set("src/main/kotlin/domain")
}


val openapi_dist = file("$projectDir/../api/src/main/resources/openapi")
val openapi_target = file("$projectDir/../../app")
tasks.register("OpenApi") {
    doLast {
        logger.warn("")
        openapi_dist.listFilesOrdered { it.isFile && it.extension == "yaml" }.forEach {
            val content = it.readText()
                .replace("'*/*':", "'application/json':")
                .replace("https://server", "http://0.0.0.0:8080")
            val distFile = "${openapi_target.absolutePath}/${it.name}"
            logger.warn("CREATING: $distFile")
            file(distFile).writeText(content)
        }
    }
}
