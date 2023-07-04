data class Parameter(
    val def: String, val ime: String, val tip: String, val rel: String?
) {

    companion object {
        fun parse(line: String): Parameter {
            val info = line
                .trim()
                .replace("override", "")
                .replace("@Contextual", "")
                .trim()
                .removeSuffix(",")
                .split(":")

            val paramInfo = info.first().split(" ")

            var type = info.last()
            var rel: String? = null
            if (type.contains("=")) type = type.split("=").first()
            if (type.contains("ObjectId") && !line.contains("override")) {
                rel = paramInfo.last().split("_").first().capitalize()
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
            for (i in 2 until lineInfo.size - 1) {
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

interface DomainMapExtension {
    val inputDir: Property<String>
}

class DomainMap : Plugin<Project> {

    val className = DomainMap::class.simpleName.toString()

    private fun relationships(packages: List<Package>): Map<String, List<String>> {
        //Relationship
        //                parent(lower case) child(lower case)
        val rel = mutableMapOf<String, MutableList<String>>()
        for (pac in packages) {
            for (dataClass in pac.dataClasses) {
                for (lastnost in dataClass.lastnosti) {
                    if (lastnost.ime.endsWith("_id")) {
                        val parent = lastnost.ime.replace("_id", "")
                        val child = dataClass.ime.toLowerCase()
                        rel
                            .getOrPut(parent, { mutableListOf() })
                            .add(child)
                    }
                }
            }
        }
        return rel
    }

    override fun apply(project: Project) {
        val extension = project.extensions.create<DomainMapExtension>(className)

        project.task(className) {
            doLast {
                val buildDir = this.project.buildDir
                val inputDir = File(this.project.projectDir, extension.inputDir.get())

                logger.warn("\nInput dir: $inputDir\n")

                val packages = mutableListOf<Package>()
                inputDir.walkTopDown().forEach {
                    if (it.isFile) {
                        logger.warn(" - ${it.name}")
                        packages.add(Package.parse(it))
                    }
                }

                logger.warn("\nBuild dir: $buildDir")

                mapOf(
                    ::plantUML to "plantuml",
                    ::typescript to "ts"
                ).forEach { File(buildDir, "domain.${it.value}").writeText(it.key(packages)) }
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
                    if (lastnost.rel != null) relations.add("${dataClass.ime} -down-> ${lastnost.rel}")
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
        val text = mutableListOf("// FILE AUTO GENERATED !!!\n")
        val response = mutableListOf("interface AdjecentRes {")
        val rel = relationships(packages)

        for (pac in packages) {
            for (dataClass in pac.dataClasses) {
                val lowerDataClassName = dataClass.ime.toLowerCase()

                //RESPONSE
                response.add("\t${lowerDataClassName}: {[key: string]: ${dataClass.ime}};")

                //INTERFACES
                text.add("interface ${dataClass.ime} {")
                for (lastnost in dataClass.lastnosti) {
                    text.add("\t${lastnost.ime}: string;")
                }
                val children = rel.getOrDefault(lowerDataClassName, mutableListOf())
                for (child in children) {
                    text.add("\t${child}: Array<String>;")
                }
                text.add("}\n")

                //ENUMS
                for (enum in dataClass.enums) {
                    text.add("enum ${dataClass.ime}${enum.ime} {")
                    for (enumVal in enum.vrednosti) {
                        text.add("\t${enumVal} = \"${enumVal}\",")
                    }
                    text.add("}\n")
                }
            }
        }
        response.add("}\n")
        text += response
        return text.joinToString(separator = "\n")
    }
}

apply<DomainMap>()
