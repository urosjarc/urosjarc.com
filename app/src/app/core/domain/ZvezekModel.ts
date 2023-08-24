import {TematikaModel} from "./TematikaModel";
import {Zvezek} from "../services/api/models/zvezek";

export interface ZvezekModel {
  zvezek: Zvezek,
  tematike: TematikaModel[]
}
