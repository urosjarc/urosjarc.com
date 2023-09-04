plugins {
    id("application")
    this.id("buildSrc.common")
}


application {
    this.mainClass.set("data.MainKt")
}



dependencies {
    this.implementation(this.project(":core"))
    this.implementation("net.sourceforge.tess4j:tess4j:5.8.0")
}
