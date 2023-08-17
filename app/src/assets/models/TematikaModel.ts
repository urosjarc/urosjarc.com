import {NalogaModel} from "./NalogaModel";

export interface TematikaModel {
  id: string,
  naslov: string,
  izbran: boolean | undefined,
  naloge: NalogaModel[]
}
