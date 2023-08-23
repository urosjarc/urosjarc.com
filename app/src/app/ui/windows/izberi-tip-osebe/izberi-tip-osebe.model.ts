import {OsebaTip, OsebaTipi} from "../../../utils/types";

export interface IzberiTipOsebeModel {
  tipi: OsebaTipi,
  callback: (tip: OsebaTip) => {}
}
