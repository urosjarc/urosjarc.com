
export function dateISO(leto: number, mesec: number, dan: number) {
  try {
    return new Date(leto, mesec, dan).toISOString().split("T")[0]
  } catch (e) {
    return null
  }
}

// export function nestedRows(tables: Array<string>, rows: Array<object>){
//   let tables = {}
//
//   for(let row of rows){
//     let tableObj = {}
//     for(let table of tables){
//
//
//     }
//   }
//
// }
