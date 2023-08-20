const path = require("path")
const fs = require("fs")

function String_capitalise(value: string): string {
  return value.charAt(0).toUpperCase() + value.slice(1)
}


class Import {
  type: string
  file: string

  constructor(type: string, file: string) {
    this.type = type;
    this.file = file;
  }

  static isLine(line: string) {
    return line.startsWith("import ")
  }

  compile() {
    return `import { ${this.type} } from ${this.file}`
  }
}

class Field {
  key: string
  type: string

  constructor(key: string, type: string) {
    this.key = key;
    this.type = type
  }

  static isLine(line: string) {
    return line.includes('?: ') || line.includes(': ')
  }

  compile() {
    return `\t${this.key}: ${this.type}`
  }
}

class ModelClass {
  name: string
  fields: Field[] = []

  constructor(name: string) {
    this.name = name;
  }

  static isLine(line: string) {
    return line.startsWith("export interface ")
  }

  addFieldLine(line: string) {
    let nice_line = line.trim()
    const key_type = nice_line.split("?: ")
    const key = (key_type.at(0) || "").trim()
    const type = (key_type.at(1) || "").trim().replaceAll(/;/g, "")
    this.fields.push(new Field(key, type))
  }

  compile(): string {
    let line = [`export interface ${this.name} {`]
    line = line.concat(...this.fields.map(field => field.compile()))
    line.push("}")
    return line.join('\n')
  }
}

class ModelFile {
  imports: Import[] = []
  required_imports: Import[] = []
  cls: ModelClass | undefined = undefined

  constructor(content: string) {
    this.init(content)
  }

  init(content: string) {
    for (const line of content.split("\n")) {
      if (Import.isLine(line)) {
        this.addImportLine(line)
      } else if (ModelClass.isLine(line)) {
        const className = line.split(" ").at(2) || ""
        this.cls = new ModelClass(className)
      } else if (Field.isLine(line) && this.cls) {
        this.cls.addFieldLine(line)
      }
    }
  }

  cleanImportsTypes(...vargs: string[]) {
    this.imports = this.imports.filter(val => !vargs.includes(val.type))
  }

  addImportLine(line: string) {
    let nice_line_infos = line.trim().split(' ')
    let type = nice_line_infos[2]
    let file = nice_line_infos[nice_line_infos.length - 1]
    this.imports.push(new Import(type, file))
  }

  replace_ignored_types_to_string(...types: string[]) {
    for (const field of this.cls?.fields || []) {
      if (types.includes(field.type)) {
        field.type = "string"
      }
    }
  }

  inject_foreign_class_to_id(...skip_foreign_types: string[]) {
    for (const field of this.cls?.fields || []) {
      if (field.key.includes("_id")) {
        const foreign_type = String_capitalise(field.key.split("_").at(0) || "")
        if (field.key.endsWith("'_id'") || field.key == "id") {
          field.type = field.type.replaceAll(/(ObjectId|Id|AnyId)/g, `Id<${this.cls?.name}>`)
        } else {
          const should_skip = skip_foreign_types.includes(foreign_type)
          const id = should_skip ? `AnyId`: `Id<${foreign_type}>`
          field.type = field.type.replaceAll(/(ObjectId|Id|AnyId)/g, id)
          if(!should_skip){
            const new_import = new Import(foreign_type, `'./${foreign_type.toLowerCase()}'`)
            this.add_required_import(new_import)
          }
        }

        const new_import = new Import("Id", "'./id'")
        this.add_required_import(new_import)
      }
    }
  }

  compile() {
    if (!this.cls) return ''
    let lines = this.required_imports.map(imprt => imprt.compile())
    lines = lines.concat(...this.imports.map(imprt => imprt.compile()))
    lines.push(this.cls.compile())
    return lines.join('\n')
  }

  private add_required_import(new_import: Import) {
    if ([...this.required_imports, ...this.imports].filter(imp => imp.type == new_import.type).length === 0) {
      this.required_imports.push(new_import)
    }
  }
}

/**
 * Main generation configuration
 */
const ngOpenApiGen = require('../ng-openapi-gen.json')

/**
 * Models informations
 */
const modelsDirPath = path.join(ngOpenApiGen.output, 'models')
const modelsImportsPath = path.join(ngOpenApiGen.output, 'models.ts')

/**
 * What should be skiped
 */
const ignored_files = ["local-date-time", "bson-binary", "object-id", 'id', "any-id", "local-date"]
const string_types = ["LocalDateTime", "LocalDate", "BsonBinary"]
const id_types = ["ObjectId"]
const skip_foreign_types = ["Entiteta", "Entitete"]
const ignored_imports = [...string_types, ...id_types]

/**
 * Start processing
 */
const modelsDir = fs.readdirSync(modelsDirPath, {withFileTypes: true})

/**
 * Get model file and clean it
 */
const model_file = new ModelFile(fs.readFileSync(modelsImportsPath).toString())
model_file.cleanImportsTypes(...ignored_imports)
fs.writeFileSync(modelsImportsPath, model_file.compile())

/**
 * Get all model files and instant clean it...
 */
console.log(`\nFixing generated files:`)
for (const modelFile of modelsDir) {

  /**
   * Ignore directories and ignored files
   */
  if (modelFile.isDirectory() && ignored_files.includes(modelFile.name)) continue

  /**
   * Create full path
   */
  const modelFilePath = path.join(modelFile.path, modelFile.name)

  /**
   * Get file content
   */
  const modelContent = fs.readFileSync(modelFilePath).toString()

  /**
   * Create model and remove ignored imports, bad types, replace id with strong typed id
   */
  const model: ModelFile = new ModelFile(modelContent)
  model.cleanImportsTypes(...ignored_imports)
  model.replace_ignored_types_to_string(...string_types)
  model.inject_foreign_class_to_id(...skip_foreign_types)

  /**
   * Write and log
   */
  const info = model.compile()
  fs.writeFileSync(modelFilePath, info)
  console.log(` + [${info.length}B] ${modelFile.name}`)
}

/**
 * Clean ignored files
 */
for (const ignored_file of ignored_files) {
  const path_file = path.join(modelsDirPath, ignored_file) + '.ts'
  console.log(` - ${ignored_file}`)
  fs.unlinkSync(path_file)
}


/**
 * Write custom files
 */
fs.writeFileSync(path.join(modelsDirPath, 'id.ts'), `interface _Id<T> { _type: T }\n\nexport type Id<T> = _Id<T> | string`)
fs.writeFileSync(path.join(modelsDirPath, 'any-id.ts'), `interface _AnyId { _type: string }\n\nexport type AnyId = _AnyId | string`)
