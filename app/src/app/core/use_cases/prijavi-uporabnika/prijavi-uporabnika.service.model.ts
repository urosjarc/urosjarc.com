import {ArrayTypes} from "../../../utils/types";
import {Oseba} from "../../services/api/models/oseba";

export interface PrijaviUporabnikaServiceModel {
  tip: ArrayTypes<Oseba['tip']>,
  sinhronizacija: boolean
}
