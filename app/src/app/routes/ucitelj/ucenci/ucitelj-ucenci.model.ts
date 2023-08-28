import {TableModel} from "../../../ui/parts/table/table.model";
import {UcenjeModel} from "../../../core/domain/UcenjeModel";
import {inject} from "@angular/core";
import {DatePipe} from "@angular/common";

export interface UciteljUcenciModel extends TableModel {
  Začetek: string,
  Učenec: string,
  Letnik: number,
}

export function uciteljUcenciModelMap(datePipe: DatePipe, ucenjeModel: UcenjeModel): UciteljUcenciModel {
  return {
    ...ucenjeModel,
    Učenec: `${ucenjeModel.oseba.ime} ${ucenjeModel.oseba.priimek}`,
    Letnik: ucenjeModel.oseba.letnik,
    Začetek: datePipe.transform(ucenjeModel.ustvarjeno) || ""
  }
}
