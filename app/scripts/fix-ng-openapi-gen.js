#!/usr/bin/env node

const path = require('path')
const fs = require('fs')
const ngOpenApiGen = require('../ng-openapi-gen.json')

const modelsPath = path.join(ngOpenApiGen.output, 'models')
const modelsDir = fs.readdirSync(modelsPath, {withFileTypes: true})

console.log(`\nScanning: ${modelsPath}`)
for (const modelFile of modelsDir) {

  const filePath = path.join(modelFile.path, modelFile.name)
  const isGarbage = ['id', "any-id", "local-date", "local-date-time", "bson-binary", "object-id"].includes(modelFile.name.split('.').at(0))

  if (isGarbage) {
    console.log(`Removed: ${filePath}`)
    fs.unlinkSync(filePath)
    continue
  }

  const content = fs.readFileSync(filePath).toString()
  let new_content = content
    .replace(/\?: /g, ": ")
    .replace(/(<Id>|<AnyId>)/g, "\<string\>")
    .replace(/: (BsonBinary|Id|ObjectId|AnyId|LocalDateTime|LocalDate);/g, ": string;")
    .replace(/import \{ (BsonBinary|Id|ObjectId|AnyId|LocalDateTime|LocalDate) } .*/g, "")
    .replace(/^\s*$/gm, "")

  console.log(`Rewrite: ${filePath}`)
  fs.writeFileSync(filePath, new_content)
}
