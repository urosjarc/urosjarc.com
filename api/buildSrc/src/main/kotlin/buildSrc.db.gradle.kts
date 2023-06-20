plugins {
    kotlin("jvm")
}

dependencies {
    implementation("com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.3")
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
}
