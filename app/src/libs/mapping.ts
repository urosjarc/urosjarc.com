export function dateISO(leto: number, mesec: number, dan: number) {
  try {
    return new Date(leto, mesec, dan).toISOString().split("T")[0]
  } catch (e) {
    return null
  }
}

export function adjacencyList(rows: Array<object>): LinkedList {
  if (rows.length == 0) return {}

  const separator = "__"
  const id_key = "_id"

  /**
   * PREPARING DATASTRUCTURES
   */
  let table_key_row_map = {} // { table: <primary key> : { row }}
  let table_row_map = {} // {table -> { row }}
  Object.keys(rows[0]).forEach(key => {
    let [table, prop] = key.split(separator, 2)
    table_row_map[table] = {}
    table_key_row_map[table] = {}
  })

  /**
   * CREATING LINKED LIST
   */
  for (let row of rows) {
    //RESET CURRENT TABLE ROW MAP
    for (let key in table_row_map) {
      table_row_map[key] = {}
    }

    //INSERT IN CURRENT TABLE ROW MAP
    for (let [key, value] of Object.entries(row)) {
      let [table, prop] = key.split(separator, 2)
      table_row_map[table][prop] = value
    }

    //INSERT EVERYTHING IN BIG STRUCTURE
    for (let [table, newRow] of Object.entries(table_row_map)) {
      table_key_row_map[table][newRow.id] = newRow
    }
  }

  //CONNECT PARENTS WITH CHILDREN
  for (let [table, id_rows] of Object.entries(table_key_row_map)) {
    for (let [id, row] of Object.entries(id_rows)) {
      for (let [key, value] of Object.entries(row)) {
        if (key.endsWith(id_key)) {
          let refTable = key.replaceAll(id_key, "")
          if(refTable in table_key_row_map){
            if(value in table_key_row_map[refTable]) {
              if (!(table in table_key_row_map[refTable][value])) {
                table_key_row_map[refTable][value][table] = []
              }
              table_key_row_map[refTable][value][table].push(row.id)
            }
          }
        }
      }
    }
  }
  return table_key_row_map
}
