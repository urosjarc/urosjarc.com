import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {ThemePalette} from "@angular/material/core";
import {Alert} from "./Alert";

@Injectable({providedIn: 'root'})
export class AlertService {

  private alertsObserver: Subject<Alert> = new Subject();

  info(vsebina: string) {
    this.alert("INFORMACIJA", vsebina, "primary");
  }

  warn(vsebina: string) {
    this.alert("OPOZORILO", vsebina, "accent");
  }

  error(vsebina: string) {
    this.alert("NAPAKA", vsebina, "warn");
  }

  private alert(naslov: string, vsebina: string, color: ThemePalette) {
    this.alertsObserver.next({naslov, vsebina, color} as Alert);
  }

  get alerts() {
    return this.alertsObserver.asObservable();
  }
}
