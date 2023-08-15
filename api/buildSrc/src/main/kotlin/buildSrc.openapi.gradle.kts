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
//                inputDir.listFiles { it.isFile && it.extension == "yaml" }.forEach {
//                    val content = it.readText()
//                        .replace("'*/*':", "'application/json':")
//                        .replace("https://server", "http://0.0.0.0:8080")
//                    val distFile = "${outputDir.absolutePath}/${it.name}"
//                    logger.warn("CREATING: $distFile")
//                    file(distFile).writeText(content)
//                }
            }
        }
    }
}

apply<OpenApi>()
