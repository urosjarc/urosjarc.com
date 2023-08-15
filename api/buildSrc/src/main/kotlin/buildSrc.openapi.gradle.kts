interface OpenApiExtension {
    val outputDir: Property<String>
    val inputDir: Property<String>
}

class OpenApi : Plugin<Project> {

    val className = OpenApi::class.simpleName.toString()

    override fun apply(project: Project) {

        val extension = project.extensions.create<OpenApiExtension>(className)

        project.task(className) {
            doLast {
                val outputDir = File(this.project.projectDir, extension.outputDir.get())
                val inputDir = File(this.project.projectDir, extension.inputDir.get())
                logger.warn("\nOutput dir: $outputDir\n")
                logger.warn("\nInput dir: $inputDir\n")
                inputDir.listFiles { file -> file.isFile && file.extension == "yaml" }?.forEach {
                    val content = it.readText()
                        .replace("'*/*':", "'application/json':")
                        .replace("url", "test")
                        .replace("""https://server""", """http://127.0.0.1:8080""")
                    val distFile = "${outputDir.absolutePath}/${it.name}"
                    logger.warn("CREATING: $distFile")
                    File(distFile).writeText(content)
                }
            }
        }
    }
}

apply<OpenApi>()
