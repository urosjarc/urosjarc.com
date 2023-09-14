import {ZvezekModel} from "../../../core/domain/ZvezekModel";
import {TematikaModel} from "../../../core/domain/TematikaModel";
import {NalogaModel} from "../../../core/domain/NalogaModel";
import {UcenjeModel} from "../../../core/domain/UcenjeModel";
import {DatePipe} from "@angular/common";
import {inject} from "@angular/core";

export interface SestaviTestUcenjeModel extends UcenjeModel {
  Začetek: string
  Učenec: string
  Letnik: number
}

export interface SestaviTestZvezekModel extends ZvezekModel {
  Tip: string
  Naslov: string
  Tematik: number
  tematikeModels: SestaviTestTematikaModel[]
}

export function sestaviTestZvezekModelMap(zvezekModel: ZvezekModel): SestaviTestZvezekModel {
  return {
    ...zvezekModel,
    Tip: zvezekModel.zvezek.tip,
    Naslov: zvezekModel.zvezek.naslov,
    Tematik: zvezekModel.tematike.length,
    tematikeModels: zvezekModel.tematike.map(tematika => sestaviTestTematikaModelMap(tematika, zvezekModel))
  }
}

export interface SestaviTestTematikaModel extends TematikaModel {
  Zvezek: string,
  Naslov: string,
  Nalog: number,
  nalogaModels: SestaviTestNalogaModel[]
}

export function sestaviTestTematikaModelMap(tematikaModel: TematikaModel, zvezekModel: ZvezekModel): SestaviTestTematikaModel {
  return {
    ...tematikaModel,
    Zvezek: zvezekModel.zvezek.naslov,
    Naslov: tematikaModel.tematika.naslov,
    Nalog: tematikaModel.naloge.length,
    nalogaModels: tematikaModel.naloge.map(naloga => sestaviTestNalogaModelMap(naloga, tematikaModel, zvezekModel))
  }
}

export interface SestaviTestNalogaModel extends NalogaModel {
  Zvezek: string,
  Tematka: string,
  Resitev: string,
  Vsebina: string
}

export function sestaviTestNalogaModelMap(nalogaModel: NalogaModel, tematikaModel: TematikaModel, zvezekModel: ZvezekModel): SestaviTestNalogaModel {
  return {
    ...nalogaModel,
    Zvezek: zvezekModel.zvezek.naslov,
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

export function sestaviTestUcenjeModelMap(datePipe: DatePipe, ucenjeModel: UcenjeModel): SestaviTestUcenjeModel {
  return {
    ...ucenjeModel,
    Začetek: datePipe.transform(ucenjeModel.ustvarjeno) || "",
    Učenec: `${ucenjeModel.oseba.ime} ${ucenjeModel.oseba.priimek}`,
    Letnik: ucenjeModel.oseba.letnik,
  }

}
