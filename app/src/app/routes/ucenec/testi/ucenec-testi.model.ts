import {TableModel} from "../../../ui/parts/table/table.model";

export interface UcenecTestiModel extends TableModel {
  Naslov: string,
  Nalog: number,
  Opravljeno: string,
  Deadline: string,
  "Čez...": string
}
