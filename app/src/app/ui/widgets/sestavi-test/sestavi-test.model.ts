import {ZvezekModel} from "../../../core/domain/ZvezekModel";
import {TematikaModel} from "../../../core/domain/TematikaModel";
import {NalogaModel} from "../../../core/domain/NalogaModel";
import {UcenjeModel} from "../../../core/domain/UcenjeModel";
import {DatePipe} from "@angular/common";
import {inject} from "@angular/core";

export interface SestaviTestUcenjeModel extends UcenjeModel {
  Začetek: string,
  Učenec: string,
  Letnik: number,
}

export interface SestaviTestModel extends ZvezekModel {
  Tip: string,
  Naslov: string,
  Tematik: number
}

export function uciteljZvezkiModelMap(zvezekModel: ZvezekModel): SestaviTestModel {
  return {
    ...zvezekModel,
    Tip: zvezekModel.zvezek.tip,
    Naslov: zvezekModel.zvezek.naslov,
    Tematik: zvezekModel.tematike.length
  }
}

export interface SestaviTestTematikaModel extends TematikaModel {
  Zvezek: string,
  Naslov: string,
  Nalog: number,
}

export function uciteljZvezkiTematikaModelMap(tematikaModel: TematikaModel, zvezekModel: ZvezekModel): SestaviTestTematikaModel {
  return {
    ...tematikaModel,
    Zvezek: zvezekModel.zvezek.naslov,
    Naslov: tematikaModel.tematika.naslov,
    Nalog: tematikaModel.naloge.length
  }
}

export interface SestaviTestNalogaModel extends NalogaModel {
  Zvezek: string,
  Tematka: string,
  Resitev: string,
  Vsebina: string
}

export function uciteljZvezkiNalogaModelMap(nalogaModel: NalogaModel, tematikaModel: SestaviTestTematikaModel): SestaviTestNalogaModel {
  return {
    ...nalogaModel,
    Zvezek: tematikaModel.Zvezek,
    Tematka: tematikaModel.tematika.naslov,
    Resitev: nalogaModel.naloga.resitev,
    Vsebina: nalogaModel.naloga.vsebina
  }
}

export interface SestaviTestUcenjeModel extends UcenjeModel {
  Začetek: string,
  Učenec: string,
  Letnik: number,
}

export function uciteljZvezkiUcenjeModelMap(ucenjeModel: UcenjeModel): SestaviTestUcenjeModel {
  const datePipe = inject(DatePipe)
  return {
    ...ucenjeModel,
    Začetek: datePipe.transform(ucenjeModel.ustvarjeno) || "",
    Učenec: `${ucenjeModel.oseba.ime} ${ucenjeModel.oseba.priimek}`,
    Letnik: ucenjeModel.oseba.letnik,
  }

}
