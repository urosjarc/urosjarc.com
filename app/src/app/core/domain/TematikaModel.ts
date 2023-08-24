import {NalogaModel} from "./NalogaModel";
import {Tematika} from "../services/api/models/tematika";

export interface TematikaModel {
  tematika: Tematika,
  naloge: NalogaModel[]
}
