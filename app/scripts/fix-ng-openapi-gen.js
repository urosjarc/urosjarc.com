#!/usr/bin/env node

const path = require('path')
const fs = require('fs')
const ngOpenApiGen = require('../ng-openapi-gen.json')


String.prototype.capitalise = function () {
  return this.charAt(0).toUpperCase() + this.slice(1)
}

const modelsPath = path.join(ngOpenApiGen.output, 'models')
const modelsDir = fs.readdirSync(modelsPath, {withFileTypes: true})

console.log(`\nScanning: ${modelsPath}`)
for (const modelFile of modelsDir) {

  const filePath = path.join(modelFile.path, modelFile.name)
  const model = modelFile.name.split('.').at(0)
  const isGarbage = ['id', "any-id", "local-date", "local-date-time", "bson-binary", "object-id"].includes(model)

  if (isGarbage) {
    console.log(`Removed: ${filePath}`)
    fs.unlinkSync(filePath)
    if(model === 'id') fs.writeFileSync(filePath, `export type Id<T> = string & {_type: T}`)
    continue
  }

  const content = fs.readFileSync(filePath).toString()
  let new_content = content
    .replace(/\?: /g, ": ")
    .replace(/(<Id>|<AnyId>)/g, "\<string\>")
    .replace(/: (BsonBinary|Id|ObjectId|AnyId|LocalDateTime|LocalDate);/g, ": string;")
    .replace(/import \{ (BsonBinary|Id|ObjectId|AnyId|LocalDateTime|LocalDate) } .*/g, "")
    .replace(/^\s*$/gm, "")

  if (!model.includes('-')) {
    let new_content_lines = [`import { Id } from './id'`]
    new_content.split("\n").forEach(line => {
      const foreign_model = line.trimLeft().split('_id').at(0).split('_').at(0)
      const foreign_class = foreign_model.capitalise()
      const model_class = model.capitalise()
      if (line.includes("'_id':")) {
        new_content_lines.push(`\t'_id': Id<${model_class}>`)
      } else if (line.includes('_id:')) {
        new_content_lines = [`import { ${foreign_class} } from './${foreign_model}'`].concat(...new_content_lines)
        new_content_lines.push(line.replace(/(string)/g, `Id<${foreign_class}>`))
      } else {
        new_content_lines.push(line)
      }
    })
    new_content = new_content_lines.join('\n')
  }

  console.log(`Rewrite: ${filePath}`)
  fs.writeFileSync(filePath, new_content)
}
