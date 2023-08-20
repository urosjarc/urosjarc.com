import {TematikaModel} from "./TematikaModel";
import {Zvezek} from "../../app/core/services/api/models/zvezek";

export interface ZvezekModel {
  id: string,
  naslov: string,
  izbran: boolean | undefined,
  tip: Zvezek['tip'],
  tematike: TematikaModel[]
}
