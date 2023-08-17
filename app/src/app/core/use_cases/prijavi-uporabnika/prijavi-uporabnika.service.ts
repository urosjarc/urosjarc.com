import {Injectable} from '@angular/core';
import {ApiService} from "../../services/api/services/api.service";
import {PrijavaReq} from "../../services/api/models/prijava-req";
import {
  SinhronizirajUporabniskePodatkeService
} from "../sinhroniziraj-uporabniske-podatke/sinhroniziraj-uporabniske-podatke.service";
import {Router} from "@angular/router";
import {ArrayTypes, exe} from "../../../utils/types";
import {Oseba} from "../../services/api/models/oseba";
import {routes} from "../../../routes";
import {Observable, Subject} from "rxjs";
import {AlertService} from "../../services/alert/alert.service";
import {OsebaData} from "../../services/api/models/oseba-data";
import {PrijavaRes} from "../../services/api/models/prijava-res";
import {IzberiTipOsebeComponent} from "../../../ui/windows/izberi-tip-osebe/izberi-tip-osebe.component";
import {MatDialog} from "@angular/material/dialog";
import {IzberiTipOsebeModel} from "../../../ui/windows/izberi-tip-osebe/izberi-tip-osebe.model";
import {DbService} from "../../services/db/db.service";

@Injectable({providedIn: 'root'})
export class PrijaviUporabnikaService {
  private cakam_redirect: Subject<PrijavaRes> = new Subject();

  get cakam_redirect_event() {
    return this.cakam_redirect.asObservable();
  }

  constructor(
    private alert: AlertService,
    private dialog: MatDialog,
    private router: Router,
    private db: DbService,
    private sinhroniziraj_uporabniske_podatke: SinhronizirajUporabniskePodatkeService,
    private api: ApiService) {
  }

  async zdaj(prijavaReq: PrijavaReq) {

    // Ustvari prijavo
    const prijavaRes = await exe(this.api.authPrijavaPost({body: prijavaReq}))

    // Shrani token da bo lahko uporabnik klical server z autorizacijo
    this.db.token = prijavaRes.token || ""

    // Sprozi event za redirect
    this.dialog.open<any, IzberiTipOsebeModel>(IzberiTipOsebeComponent, {
      enterAnimationDuration: 250,
      exitAnimationDuration: 500,
      data: {
        callback: this.redirect,
        tipi: prijavaRes.tip
      }
    });

  }

  async redirect(tip: ArrayTypes<Oseba['tip']>) {

    /**
     * Izberi primeren route za redirect
     */
    let clientRoute: { $: string } | null = null
    let serverRoute: Observable<OsebaData> | null = null
    switch (tip) {
      case "UCENEC":
        clientRoute = routes.ucenec({});
        serverRoute = this.api.ucenecGet()
        break;
      case "UCITELJ":
        clientRoute = routes.ucitelj({});
        serverRoute = this.api.uciteljGet()
        break;
      case "ADMIN":
        clientRoute = routes.ucenec({});
        serverRoute = this.api.adminGet()
        break;
    }

    /**
     * Ce ima neprimerno ali manjkajoco avtorizacijo prikazi alert.
     */
    if (!clientRoute || !serverRoute)
      return this.alert.warnManjkajocaAvtorizacija()

    // Dobi vse uporabniske podatke
    const osebaData = await exe(serverRoute)

    // Sinhroniziraj uporabniske podatke
    this.sinhroniziraj_uporabniske_podatke.zdaj(osebaData)
  }
}
