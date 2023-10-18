plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    this.id("buildSrc.common")
    this.id("buildSrc.serialization")
    this.id("buildSrc.injections")
    this.id("buildSrc.datetime")
}

application {
    this.mainClass.set("gui.app.windows.Procesiranje_zip_zvezkov")
}

javafx {
    version = "18.0.2"
    modules("javafx.controls", "javafx.fxml")
}

dependencies {
    this.implementation(this.project(":core"))
    this.implementation("org.jfxtras:jmetro:11.6.14")
    this.implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.7.3")
    this.implementation("org.jsoup:jsoup:1.16.1")
    this.implementation("net.coobird:thumbnailator:0.4.20")
    this.implementation("com.google.cloud:google-cloud-vision:3.21.0")
    this.implementation("net.sourceforge.tess4j:tess4j:5.8.0")
}
