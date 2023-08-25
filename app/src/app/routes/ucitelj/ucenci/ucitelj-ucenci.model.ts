import {TableModel} from "../../../ui/parts/table/table.model";

export interface UciteljUcenciModel extends TableModel{
  Začetek: string,
  Učenec: string,
  Letnik: number,
}
