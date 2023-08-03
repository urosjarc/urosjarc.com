import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {ThemePalette} from "@angular/material/core";
import {Alert} from "./Alert";

@Injectable({providedIn: 'root'})
export class AlertService {

  private alertsObserver: Subject<Alert> = new Subject();

  info(naslov: string, vsebina: string) {
    this.alert(naslov, vsebina, "primary");
  }

  warn(naslov: string, vsebina: string) {
    this.alert(naslov, vsebina, "accent");
  }

  error(naslov: string, vsebina: string) {
    this.alert(naslov, vsebina, "warn");
  }

  private alert(naslov: string, vsebina: string, color: ThemePalette) {
    this.alertsObserver.next({naslov, vsebina, color} as Alert);
  }

  get alerts() {
    return this.alertsObserver.asObservable();
  }
}
