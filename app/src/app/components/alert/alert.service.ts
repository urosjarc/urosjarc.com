import {Injectable} from '@angular/core';
import {BehaviorSubject, Subject} from "rxjs";
import {Alert, AlertTip} from "./alert";

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  constructor() {
  }

  private alertsObserver: Subject<Alert> = new BehaviorSubject({tip: AlertTip.INFO, vsebina: "", trajanje: 0} as Alert);

  info(message: string, trajanje: number = 1) {
    this.alert(message, AlertTip.INFO, trajanje);
  }

  warn(message: string, trajanje: number = 1) {
    this.alert(message, AlertTip.WARN, trajanje);
  }

  error(message: string, trajanje: number = 1) {
    this.alert(message, AlertTip.ERROR, trajanje);
  }

  private alert(vsebina: string, tip: AlertTip, trajanje: number) {
    trajanje = !trajanje ? 3000 : trajanje;
    this.alertsObserver.next({tip, vsebina, trajanje} as Alert);
  }

  get alerts() {
    return this.alertsObserver.asObservable();
  }
}
