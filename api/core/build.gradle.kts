import org.gradle.kotlin.dsl.support.listFilesOrdered

plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.serialization")
    this.id("buildSrc.datetime")
}

//configure<BuildSrc_domainMap_gradle.DomainMapExtension> {
//    this.inputDir.set("src/commonMain/kotlin/domain")
//}


val openapi_dist = file("$projectDir/../api/src/main/resources/openapi")
val openapi_target = file("$projectDir/../../app/src/api")
tasks.register("client") {
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
