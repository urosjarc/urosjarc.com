import {ZvezekModel} from "../../../core/domain/ZvezekModel";
import {TematikaModel} from "../../../core/domain/TematikaModel";
import {NalogaModel} from "../../../core/domain/NalogaModel";
import {UcenjeModel} from "../../../core/domain/UcenjeModel";
import {DatePipe} from "@angular/common";
import {inject} from "@angular/core";

export interface UciteljZvezkiModel extends ZvezekModel {
  Tip: string,
  Naslov: string,
  Tematik: number
}

export function uciteljZvezkiModelMap(zvezekModel: ZvezekModel): UciteljZvezkiModel {
  return {
    ...zvezekModel,
    Tip: zvezekModel.zvezek.tip,
    Naslov: zvezekModel.zvezek.naslov,
    Tematik: zvezekModel.tematike.length
  }
}

export interface UciteljZvezkiTematikaModel extends TematikaModel {
  Zvezek: string,
  Naslov: string,
  Nalog: number,
}

export function uciteljZvezkiTematikaModelMap(tematikaModel: TematikaModel, zvezekModel: ZvezekModel): UciteljZvezkiTematikaModel {
  return {
    ...tematikaModel,
    Zvezek: zvezekModel.zvezek.naslov,
    Naslov: tematikaModel.tematika.naslov,
    Nalog: tematikaModel.naloge.length
  }
}

export interface UciteljZvezkiNalogaModel extends NalogaModel {
  Zvezek: string,
  Tematka: string,
  Resitev: string,
  Vsebina: string
}

export function uciteljZvezkiNalogaModel(nalogaModel: NalogaModel, tematikaModel: UciteljZvezkiTematikaModel): UciteljZvezkiNalogaModel {
  return {
    ...nalogaModel,
    Zvezek: tematikaModel.Zvezek,
    Tematka: tematikaModel.tematika.naslov,
    Resitev: nalogaModel.naloga.resitev,
    Vsebina: nalogaModel.naloga.vsebina
  }
}

export interface UciteljZvezkiUcenjeModel extends UcenjeModel {
  Začetek: string,
  Učenec: string,
  Letnik: number,
}

export function uciteljZvezkiUcenjeModel(ucenjeModel: UcenjeModel): UciteljZvezkiUcenjeModel {
  const datePipe = inject(DatePipe)
  return {
    ...ucenjeModel,
    Začetek: datePipe.transform(ucenjeModel.ustvarjeno) || "",
    Učenec: `${ucenjeModel.oseba.ime} ${ucenjeModel.oseba.priimek}`,
    Letnik: ucenjeModel.oseba.letnik,
  }
}
