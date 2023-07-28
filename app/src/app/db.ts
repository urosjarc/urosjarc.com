// db.ts
import Dexie, {Table} from 'dexie';
import {UcenecData} from "./api";

export class AppDB extends Dexie {
  ucenecData!: Table<UcenecData, number>

  constructor() {
    super('ngdexieliveQuery')
    this.version(3).stores({
      ucenecData: "id++"
    })
  }
}

export const db = new AppDB()
