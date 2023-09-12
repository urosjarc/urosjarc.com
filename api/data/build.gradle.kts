plugins {
    id("application")
    this.id("buildSrc.common")
    this.id("buildSrc.serialization")
}


application {
    this.mainClass.set("data.MainKt")
}

dependencies {
    this.implementation(this.project(":core"))
    this.implementation("org.jsoup:jsoup:1.16.1")
    this.implementation("net.coobird:thumbnailator:0.4.20")
    this.implementation("com.google.cloud:google-cloud-vision:3.21.0")
    this.implementation("net.sourceforge.tess4j:tess4j:5.8.0")
    this.implementation("it.sauronsoftware:ftp4j:1.6")
}
