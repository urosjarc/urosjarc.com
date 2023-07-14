plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.datetime")
    this.id("buildSrc.serialization")
    this.id("buildSrc.logging")
    this.id("buildSrc.injections")
    this.id("io.ktor.plugin") version "2.2.3"
}

dependencies {
    this.implementation(this.project(":core"))
    this.implementation(this.project(":app"))

    this.implementation("io.insert-koin:koin-ktor:3.3.0")
    this.implementation("io.sentry:sentry:6.25.0")

    val ktor_version = "2.2.3"
    this.implementation("io.ktor:ktor-server-cors:$ktor_version")
    this.implementation("io.ktor:ktor-server-resources:$ktor_version")
    this.implementation("io.ktor:ktor-server-sessions-jvm:$ktor_version")
    this.implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    this.implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    this.implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    this.implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    this.implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    this.implementation("io.ktor:ktor-server-auth:$ktor_version")
    this.implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    this.implementation("io.ktor:ktor-server-call-logging:$ktor_version")
}

application {
    this.applicationDefaultJvmArgs = listOf(
        "-Dio.netty.tryReflectionSetAccessible=true",
        "-Dio.ktor.development=true"
    )
    this.mainClass.set("api.MainKt")
}

tasks {
    this.create("stage").dependsOn("installDist")
}
