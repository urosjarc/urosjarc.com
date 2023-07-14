import org.gradle.kotlin.dsl.support.listFilesOrdered

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

val js_dist = file("$projectDir/build/distributions/assets/js")
val js_target = file("$projectDir/../../app/static/js")

val ts_dist = file("$projectDir/build/compileSync/js/main/productionExecutable/kotlin/")
val ts_target = file("$projectDir/../../app/src/types")

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.202-kotlin-1.5.0")
            }
        }
    }

    jvm {}

    js(IR) {
        useCommonJs()
        browser {
            webpackTask {
                destinationDirectory = js_dist
            }
        }
        binaries.executable()
    }
}
group = "si.urosjarc"
version = "1.0-SNAPSHOT"

configure<BuildSrc_domainMap_gradle.DomainMapExtension> {
    this.inputDir.set("src/commonMain/kotlin/domain")
}

tasks.register("client") {
    dependsOn("jsBrowserProductionWebpack")
    doLast {
        logger.warn("")
        js_dist.listFilesOrdered { it.isFile && listOf("js", "map").contains(it.extension) }.forEach {
            val distFile = "${js_target.absolutePath}/${it.name}"
            logger.warn("MOVING: $distFile")
            it.copyTo(file(distFile), overwrite = true)
        }
        ts_dist.listFilesOrdered { it.isFile && it.extension == "ts" }.forEach {
            val distFile = "${ts_target.absolutePath}/${it.name}"
            logger.warn("MOVING: $distFile")
            it.copyTo(file(distFile), overwrite = true)
        }
    }
}
