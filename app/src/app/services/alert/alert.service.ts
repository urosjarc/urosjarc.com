import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {ThemePalette} from "@angular/material/core";
import {Alert} from "./Alert";
import {NGXLogger} from "ngx-logger";
import {trace} from "../../utils";

@Injectable({providedIn: 'root'})
export class AlertService {

  private alertsObserver: Subject<Alert> = new Subject();

  @trace()
  info(naslov: string, vsebina: string) {
    this.alert(naslov, vsebina, "primary");
  }

  @trace()
  warn(naslov: string, vsebina: string) {
    this.alert(naslov, vsebina, "accent");
  }

  @trace()
  error(naslov: string, vsebina: string) {
    this.alert(naslov, vsebina, "warn");
  }

  @trace()
  private alert(naslov: string, vsebina: string, color: ThemePalette) {
    this.alertsObserver.next({naslov, vsebina, color} as Alert);
  }

  get alerts() {
    return this.alertsObserver.asObservable();
  }
}
