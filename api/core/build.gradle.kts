plugins {
    this.id("buildSrc.common")
    this.id("buildSrc.logging")
    this.id("buildSrc.datetime")
    this.id("buildSrc.injections")
}

dependencies {
    this.implementation("io.github.serpro69:kotlin-faker:1.14.0-rc.2")
}

data class Par(
    val def: String,
    val ime: String,
    val tip: String
) {
    companion object {
        fun parse(line: String): Par {
            val info = line.trim().removeSuffix(",").split(":")
            val paramInfo = info.first().split(" ")
            var type = info.last()
            if (type.contains("=")) {
                type = type.split("=").first()
            }
            return Par(def = paramInfo.first(), ime = paramInfo.last(), tip = type.trim())
        }
    }
}

data class Enu(
    val ime: String,
    val vrednosti: MutableList<String> = mutableListOf()
)

data class Cls(
    val ime: String,
    val lastnosti: MutableList<Par> = mutableListOf(),
    var enums: MutableList<Enu> = mutableListOf()
) {
    companion object {

        fun parseProperties(propertiesTxt: String): Cls {
            val classInfo = propertiesTxt.split("(", limit = 2)
            val cls = Cls(ime = classInfo.first())
            val clsVsebina = classInfo.last().split("\n")
            for (line in clsVsebina) {
                if (line.contains(":")) {
                    cls.lastnosti.add(Par.parse(line))
                }
            }
            return cls
        }

        fun parseBody(classBodyTxt: String): MutableList<Enu> {
            val enums = mutableListOf<Enu>()
            for (line in classBodyTxt.split("\n")) {
                val cleanLine = line.trim()
                if (cleanLine.startsWith("enum class")) {
                    val lineInfo = cleanLine.split(" ")
                    val enum = Enu(ime = lineInfo[2].split("=").first())
                    for (i in 3 until lineInfo.size) {
                        var vrednost = lineInfo[i].replace("{", "")
                            .replace("}", "")
                            .replace(",", "")
                            .replace(" ", "").trim()
                        if (vrednost.isNotEmpty()) enum.vrednosti.add(vrednost)
                    }
                    enums.add(enum)
                }
            }
            return enums
        }

        fun parse(txt: String): Cls {
            val classTxt = txt.split("data class ").last()
            val classInfo = classTxt.split(" : Entiteta")
            val propertiesTxt = classInfo.first()
            val classBody = classInfo.last()
            val cls = parseProperties(propertiesTxt)
            cls.enums = parseBody(classBody)
            return cls
        }
    }
}

// Use the default greeting
tasks.register<DefaultTask>("domainMap") {
    val root = this.project.projectDir
    val path = "src/main/kotlin/si/urosjarc/server/core/domain"
    val domain = File(root, path)
    domain.walkTopDown().forEach {
        if (it.isFile) {
            val cls = Cls.parse(it.readText())
            println(cls.ime)
            for (l in cls.lastnosti) {
                println("\t$l")
            }
            for (l in cls.enums) {
                println("\t$l")
            }
        }
    }
}
