import {Oseba} from "../../../core/services/api/models/oseba";
import {ArrayTypes} from "../../../utils/types";

export interface IzberiTipOsebeModel {
  tipi: Oseba['tip'],
  callback: (tip: ArrayTypes<Oseba['tip']>) => {}
}
