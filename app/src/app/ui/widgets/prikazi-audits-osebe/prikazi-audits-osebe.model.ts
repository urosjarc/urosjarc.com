import {TableModel} from "../../parts/table/table.model";

export interface PrikaziAuditsOsebeModel extends TableModel {
  Tip: string,
  Opis: string,
  Trajanje: string,
  Ustvarjeno: string,
  "Pred...": string
}
