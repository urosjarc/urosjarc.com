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

data class Par(
    val def: String, val ime: String, val tip: String, val rel: String?
) {

    fun planUML(): String = "\t${this.ime}\n"

    companion object {
        fun parse(line: String): Par {
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
            return Par(def = paramInfo.first(), ime = paramInfo.last(), tip = type.trim(), rel = rel)
        }
    }
}

data class Enu(
    val ime: String, val vrednosti: MutableList<String> = mutableListOf()
) {
    fun plantUML() {

    }

    companion object {
        fun parse(line: String): Enu {
            val lineInfo = line.trim().removePrefix("enum class ").replace(",", "").split(" ")
            val enum = Enu(ime = lineInfo.first())
            for (i in 2 until lineInfo.size) {
                enum.vrednosti.add(lineInfo[i])
            }
            return enum
        }
    }
}

data class Cls(
    val ime: String = "",
    val lastnosti: MutableList<Par> = mutableListOf(),
    var enums: MutableList<Enu> = mutableListOf()
) {
    fun class_plantUML(includePar: Boolean): String {
        var text = "class $ime {\n"
        for (l in lastnosti) {
            if (includePar) text += l.planUML()
        }
        text += "}\n"
        return text
    }

    fun rel_plantUML(includeLabels: Boolean): String {
        var rel = ""
        for (l in lastnosti) {
            if (l.rel != null) {
                rel += "${this.ime} --> ${l.rel}"
                rel += if (includeLabels) ": ${l.rel}\n" else "\n"
            }
        }
        return rel
    }

    companion object {
        fun parseName(line: String): String = line.split(" ").last().removeSuffix("(")
        fun parse(file: File): MutableList<Cls> {
            val clses = mutableListOf<Cls>()
            for (line in file.readLines()) {
                val cleanLine = line.trim()
                println(cleanLine)
                if (cleanLine.startsWith("data class")) {
                    clses.add(Cls(ime = this.parseName(cleanLine)))
                    continue
                }

                if (clses.isEmpty()) continue

                if (cleanLine.contains("val ") || cleanLine.contains("var ")) {
                    clses.last().lastnosti.add(Par.parse(cleanLine))
                    continue
                }
                if (cleanLine.startsWith("enum class")) {
                    clses.last().enums.add(Enu.parse(cleanLine))
                    continue
                }
            }
            return clses
        }
    }
}

// Use the default greeting
tasks.register<DefaultTask>("domainMap") {
    val root = this.project.projectDir
    val build = this.project.buildDir
    val path = "src/main/kotlin/si/urosjarc/server/core/domain"
    val domain = File(root, path)

    var plantUML = """
        @startuml
        skinparam backgroundColor darkgray 
        skinparam ClassBackgroundColor lightgray
        
        
    """.trimIndent()

    var relations = ""
    domain.walkTopDown().forEach {
        if (it.isFile) {
            val cls = Cls.parse(it)
            if (cls.size > 1) plantUML += "package ${it.nameWithoutExtension}-KT <<Folder>> {\n\n"
            for (c in cls) {
                plantUML += c.class_plantUML(includePar = true)
                relations += c.rel_plantUML(includeLabels = false)
            }
            if (cls.size > 1) plantUML += "\n}\n\n"
        }
    }
    plantUML += "\n$relations\n@enduml"

    File(build, "domain.plantuml").writeText(plantUML)
}
