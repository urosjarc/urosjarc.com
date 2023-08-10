import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {ThemePalette} from "@angular/material/core";
import {Alert} from "./Alert";
import {trace} from "../../utils";
import {HttpErrorResponse} from "@angular/common/http";
import {KontaktObrazecRes} from "../api/openapi/models/kontakt-obrazec-res";

@Injectable({providedIn: 'root'})
export class AlertService {

  private alertsObserver: Subject<Alert> = new Subject();

  @trace()
  private info(naslov: string, vsebina: string) {
    this.alert(naslov, vsebina, "primary");
  }

  @trace()
  private warn(naslov: string, vsebina: string) {
    this.alert(naslov, vsebina, "accent");
  }

  @trace()
  private error(naslov: string, vsebina: string) {
    this.alert(naslov, vsebina, "warn");
  }

  @trace()
  private alert(naslov: string, vsebina: string, color: ThemePalette) {
    this.alertsObserver.next({naslov, vsebina, color} as Alert);
  }

  get alerts() {
    return this.alertsObserver.asObservable();
  }

  serverNiDostopen(err: HttpErrorResponse) {
    const msg = err.message
    this.error("SERVER NEDOSTOPEN", `
      Komunikacija z serverjem ni bila vspostavljena! Incident se ni mogel registrirati!<br>
      Preverite internetno povezavo in če je vspostavljena, mi nujno posredujte vse informacije<br>
      o napaki preko telefonskega klica, ali email-a v primeru nedoseglivosti.<br>
      Za vse nevšečnosti se Vam iskreno opravičujem!<br>
      <br>
      ${msg}
    `)
  }

  sinhronizacijskaNapaka(err: Error) {
    const msg = err.message
    this.error("KRITICNA SINHRONIZACIJSKA NAPAKA", `
      Zgodila se je kriticna sinhronizacijska napaka! Incident se je registriral!<br>
      Če želite da se Vas obvesti, ko bo napaka rešena, mi posredujte vašo željo preko email-a ali sms-a.<br>
      Za vse nevšečnosti se Vam iskreno opravičujem!<br>
      <br>
      ${msg}
    `)
  }

  serverNapaka(err: HttpErrorResponse) {
    const msg = err.message
    this.error("KRITIČNA NAPAKA", `
      Zgodila se je kritična napaka! Incident se je registriral!<br>
      Če želite da se Vas obvesti, ko bo napaka rešena, mi posredujte vašo željo preko email-a ali sms-a.<br>
      Za vse nevšečnosti se Vam iskreno opravičujem!<br>
      <br>
      ${msg}
    `)
  }

  neuspesnaPrijava() {
    this.info("Prijava ni bila uspešna!", "")
  }

  uporabniskaNapaka(err: HttpErrorResponse) {
    this.warn(err.error.info, `
      Aplikacijo uporabljate na napačen način.<br>
      Incident se je zabeležil!
    `)
  }


  manjkajocaAvtorizacija() {
    this.warn(
      "Neaktivno avtorizacijsko dovoljenje!", `
      Uspešno ste se prijavili.
      <br>
      Vendar nimate aktiviranega avtorizacijskega dovoljenja, da bi lahko nadaljevali.
      Če verjamete da je to napaka, me o tem obvestite.
    `)
  }

  sprejetnoSporocilo(obrazec: KontaktObrazecRes) {
    this.info('Vaše sporočilo je bilo sprejeto!', `
      Preverite prejem potrditvenih sporočil.<br>
      <br>
      <h3>
        Email: ${obrazec.email?.data}
        <br>
        Telefon: ${obrazec.telefon?.data}
      </h3>
    `)
  }
}
