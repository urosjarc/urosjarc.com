import {NalogaInfo} from "./NalogaInfo";

export interface TematikaInfo {
  id: string,
  naslov: string,
  izbran: boolean,
  naloge: NalogaInfo[]
}
