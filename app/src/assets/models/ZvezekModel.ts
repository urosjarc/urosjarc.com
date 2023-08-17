import {TematikaModel} from "./TematikaModel";
import {Zvezek} from "../services/api/openapi/models/zvezek";

export interface ZvezekModel {
  id: string,
  naslov: string,
  izbran: boolean | undefined,
  tip: Zvezek['tip'],
  tematike: TematikaModel[]
}
