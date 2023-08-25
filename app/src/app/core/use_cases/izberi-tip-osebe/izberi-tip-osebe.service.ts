import {Injectable} from '@angular/core';
import {IzberiTipOsebeComponent} from "../../../ui/windows/izberi-tip-osebe/izberi-tip-osebe.component";
import {OsebaTip, UseCase} from "../../../utils/types";
import {MatDialogRef} from "@angular/material/dialog";
import {WindowService} from "../../services/window/window.service";

@Injectable()
export class IzberiTipOsebeService implements UseCase {
  private dialogRef?: MatDialogRef<IzberiTipOsebeComponent>

  constructor(private window: WindowService) {
  }

  zdaj(tipi: OsebaTip[]): Promise<OsebaTip> {
    return new Promise((resolve) => {
      // Ce ima uporabnik samo en tip potem takoj sprozi redirect.
      if (tipi.length == 1) {
        return resolve(tipi[0])
      }

      // Sprozi event za redirect
      this.dialogRef = this.window.odpri(IzberiTipOsebeComponent, {
        tipi: tipi,
        callback: async (tip: OsebaTip) => {
          resolve(tip)
        },
      });
    })
  }
}
