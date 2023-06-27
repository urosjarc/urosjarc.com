plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.datetime")
    this.id("buildSrc.injections")
    this.id("buildSrc.serialization")
}

dependencies {
    this.implementation("io.github.serpro69:kotlin-faker:1.14.0-rc.2")
}

data class Parameter(
    val def: String, val ime: String, val tip: String, val rel: String?
) {

    companion object {
        fun parse(line: String): Parameter {
            val info = line.trim().removeSuffix("override").trim().removeSuffix(",").split(":")
            val paramInfo = info.first().split(" ")
            var type = info.last()
            var rel: String? = null
            if (type.contains("=")) {
                type = type.split("=").first()
            }

            if (type.contains("Id<") && !line.contains("override")) {
                rel = type.split("Id<").last().split(">").first()
                rel = rel.split("<").first()
            }
            return Parameter(def = paramInfo.first(), ime = paramInfo.last(), tip = type.trim(), rel = rel)
        }
    }
}

data class Enumerator(
    val ime: String, val vrednosti: MutableList<String> = mutableListOf()
) {
    companion object {
        fun parse(line: String): Enumerator {
            val lineInfo = line.trim().removePrefix("enum class ").replace(",", "").split(" ")
            val enum = Enumerator(ime = lineInfo.first())
            for (i in 2 until lineInfo.size) {
                enum.vrednosti.add(lineInfo[i])
            }
            return enum
        }
    }
}

data class DataClass(
    val ime: String,
    val file: File,
    val lastnosti: MutableList<Parameter> = mutableListOf(),
    var enums: MutableList<Enumerator> = mutableListOf()
)

data class Package(
    val dataClasses: MutableList<DataClass>,
    val file: File
) {
    companion object {
        private fun parseClassName(line: String): String = line.split(" ").last().removeSuffix("(")
        fun parse(file: File): Package {
            val dataClasses = mutableListOf<DataClass>()
            for (line in file.readLines()) {

                val cleanLine = line.trim()

                if (cleanLine.startsWith("data class")) {
                    dataClasses.add(DataClass(ime = this.parseClassName(cleanLine), file = file))
                    continue
                }

                if (dataClasses.isEmpty()) continue

                if (cleanLine.contains("val ") || cleanLine.contains("var ")) {
                    dataClasses.last().lastnosti.add(Parameter.parse(cleanLine))
                    continue
                }
                if (cleanLine.startsWith("enum class")) {
                    dataClasses.last().enums.add(Enumerator.parse(cleanLine))
                    continue
                }
            }
            return Package(dataClasses = dataClasses, file = file)
        }
    }
}

fun plantUML(packages: List<Package>): String {
    val text = mutableListOf(
        "@startuml",
        "skinparam backgroundColor darkgray",
        "skinparam ClassBackgroundColor lightgray\n"
    )
    val relations = mutableListOf<String>()
    for (pac in packages) {
        val insertPac = pac.dataClasses.size > 1
        var indent = ""
        if (insertPac) {
            text.add("package ${pac.file.nameWithoutExtension}-KT <<Folder>> {\n")
            indent = "\t"
        }
        for (dataClass in pac.dataClasses) {
            text.add("${indent}class ${dataClass.ime} {")
            for (lastnost in dataClass.lastnosti) {
                text.add("${indent}\t${lastnost.ime}")
                if (lastnost.rel != null) relations.add("${dataClass.ime} --> ${lastnost.rel}")
            }
            text.add("${indent}}\n")
        }
        if (insertPac) text.add("}\n")
    }
    text += relations
    text.add("@enduml")
    return text.joinToString(separator = "\n")
}

fun typescript(packages: List<Package>): String {
    val text = mutableListOf<String>()
    val response = mutableListOf("interface Response {")

    //Relationship
    //                      parent  child
    val rel = mutableListOf<String, String>()
    for (pac in packages) {
        for (dataClass in pac.dataClasses) {
            for (lastnost in dataClass.lastnosti) {
                if(lastnost.ime.endsWith("_id")){
                    val parent = lastnost.ime.replace("_id", "")
                    val child = dataClass.ime
                    rel[parent] = child
                }
            }
        }
    }
    for (pac in packages) {
        for (dataClass in pac.dataClasses) {
            text.add("interface ${dataClass.ime} {")
            response.add("\t${dataClass.ime.toLowerCase()}: {[key: string]: ${dataClass.ime}};")
            for (lastnost in dataClass.lastnosti) {
                text.add("\t${lastnost.ime}: string;")
            }
            //TODO: Build relationships here!
            text.add("}\n")
        }
    }
    response.add("}\n")
    text += response
    return text.joinToString(separator = "\n")
}

// Use the default greeting
tasks.register<DefaultTask>("domainMap") {
    val root = this.project.projectDir
    val build = this.project.buildDir
    val domain = File(root, "src/main/kotlin/si/urosjarc/server/core/domain")
    val packages = mutableListOf<Package>()

    domain.walkTopDown().forEach {
        if (it.isFile) packages.add(Package.parse(it))
    }

    File(build, "domain2.plantuml").writeText(plantUML(packages))
    File(build, "domain2.ts").writeText(typescript(packages))
}
