export function dateISO(leto: number, mesec: number, dan: number) {
  try {
    return new Date(leto, mesec, dan).toISOString().split("T")[0]
  } catch (e) {
    return null
  }
}

export function linkedList(rows: Array<object>) {
  if (rows.length == 0) return []

  let table_key_row_map = {} // { table: <primary key> : { row }}
  let table_row_map = {} // {table -> { row }}

  Object.keys(rows[0]).forEach(key => {
    let [table, prop] = key.split("_", 2)
    table_row_map[table] = {}
    table_key_row_map[table] = {}
  })

  for (let row of rows) {
    const new_table_row = Object.assign(table_row_map)

    for (let [key, value] of Object.entries(row)) {
      let [table, prop] = key.split("_", 2)
      new_table_row[table][prop] = value
    }

    for (let [table, row] of Object.entries(new_table_row))
      table_key_row_map[table][row.id] = row
  }

  return table_key_row_map
}
