import {TematikaInfo} from "./TematikaInfo";

export interface ZvezekInfo {
  id: string,
  naslov: string,
  izbran: boolean,
  tematike: TematikaInfo[]
}
