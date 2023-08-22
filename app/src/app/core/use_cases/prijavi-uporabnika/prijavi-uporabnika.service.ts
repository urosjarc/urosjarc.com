import {Injectable} from '@angular/core';
import {ApiService} from "../../services/api/services/api.service";
import {PrijavaReq} from "../../services/api/models/prijava-req";
import {
  SinhronizirajUporabniskePodatkeService
} from "../sinhroniziraj-uporabniske-podatke/sinhroniziraj-uporabniske-podatke.service";
import {Router} from "@angular/router";
import {ArrayTypes, exe} from "../../../utils/types";
import {Oseba} from "../../services/api/models/oseba";
import {appUrls} from "../../../app.urls";
import {Observable} from "rxjs";
import {AlertService} from "../../services/alert/alert.service";
import {OsebaData} from "../../services/api/models/oseba-data";
import {IzberiTipOsebeComponent} from "../../../ui/windows/izberi-tip-osebe/izberi-tip-osebe.component";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {DbService} from "../../services/db/db.service";
import {trace} from "../../../utils/trace";

@Injectable()
export class PrijaviUporabnikaService {
  private dialogRef?: MatDialogRef<IzberiTipOsebeComponent>

  constructor(
    private alert: AlertService,
    private dialog: MatDialog,
    private router: Router,
    private db: DbService,
    private sinhroniziraj_uporabniske_podatke: SinhronizirajUporabniskePodatkeService,
    private api: ApiService) {
  }

  @trace()
  async zdaj(prijavaReq: PrijavaReq) {

    // Ustvari prijavo
    const prijavaRes = await exe(this.api.authPrijavaPost({body: prijavaReq}))

    // Shrani token da bo lahko uporabnik klical server z autorizacijo
    this.db.set_token(prijavaRes.token || "")

    // Ce ima uporabnik samo en tip potem takoj sprozi redirect.
    if(prijavaRes.tip.length == 1){
      return await this.redirect(prijavaRes.tip[0], true)
    }

    // Sprozi event za redirect
    this.dialogRef = this.dialog.open(IzberiTipOsebeComponent, {
      enterAnimationDuration: 250,
      exitAnimationDuration: 500,
      data: {
        callback: async (tip: ArrayTypes<Oseba['tip']>) => {
          await this.redirect(tip, true)
        },
        tipi: prijavaRes.tip
      }
    });
  }

  @trace()
  async redirect(tip: ArrayTypes<Oseba['tip']>, sinhroniziraj: boolean) {
    // Izberi primeren route za redirect
    let clientRoute: { $: string } | null = null
    let serverRoute: Observable<OsebaData> | null = null
    switch (tip) {
      case "UCENEC":
        clientRoute = appUrls.ucenec({});
        serverRoute = this.api.ucenecGet()
        break;
      case "UCITELJ":
        clientRoute = appUrls.ucitelj({});
        serverRoute = this.api.uciteljGet()
        break;
      case "ADMIN":
        clientRoute = appUrls.ucenec({});
        serverRoute = this.api.adminGet()
        break;
    }

    // Ce ima neprimerno ali manjkajoco avtorizacijo prikazi alert.
    if (!clientRoute || !serverRoute){
      this.dialogRef?.close()
      return this.alert.warnManjkajocaAvtorizacija()
    }


    if (sinhroniziraj) {
      // Dobi vse uporabniske podatke
      const osebaData = await exe(serverRoute)

      // Sinhroniziraj uporabniske podatke
      await this.sinhroniziraj_uporabniske_podatke.zdaj(osebaData)
    }

    //Zapri dialog ki si ga odprl
    this.dialogRef?.close()

    //Ustvari koncni redirect uporabnika na izbrano lokacijo
    return this.router.navigate([clientRoute.$])

  }
}
