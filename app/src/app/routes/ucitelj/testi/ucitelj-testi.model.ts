import {TableModel} from "../../../ui/parts/table/table.model";

export interface UciteljTestiModel extends TableModel {
  Naslov: string,
  Nalog : number,
  Učencev: number,
  Opravljeno: string,
  Deadline: string,
  "Čez...": string
}
