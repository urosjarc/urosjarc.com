plugins {
    id("application")
    this.id("buildSrc.common")
}


application {
    this.mainClass.set("data.MainKt")
}



dependencies {
    this.implementation(this.project(":core"))
    this.implementation("org.imgscalr:imgscalr-lib:4.2")
}
