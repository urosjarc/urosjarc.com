plugins {
    kotlin("jvm")
}

dependencies {
    // - Logging librarires
    implementation("javax.mail:mail:1.4.7")
    runtimeOnly("mysql:mysql-connector-java:8.0.33")
    runtimeOnly("org.apache.logging.log4j:log4j-jdbc-dbcp2:2.20.0")

    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
    implementation("org.apache.logging.log4j:log4j-core:2.15.0")
    implementation("org.apache.logging.log4j:log4j-layout-template-json:2.15.0")


}
