import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {db} from "./db";
import {Injectable} from "@angular/core";
import {AlertService} from "./services/alert/alert.service";
import {ApiService} from "./api/services/api.service";

@Injectable()
export class ApiInterceptor implements HttpInterceptor {
  alertService: AlertService

  constructor(alertService: AlertService) {
    this.alertService = alertService
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const self = this

    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${db.get_token()}`
      }
    });
    return next.handle(req).pipe(
      tap(x => x, (err: HttpErrorResponse) => {

        if (err.url?.endsWith(ApiService.AuthProfilGetPath)) return

        if (err.error) {
          if (err.status == 404 || err.status >= 500) this.serverNapaka(err)
          else this.uporabniskaNapaka(err)
        } else {
          this.serverNapaka(err)
        }

      })
    );
  }

  serverNapaka(err: HttpErrorResponse) {
    const msg = err.error ? `${err.error.status}: ${err.error.info}` : err.message
    this.alertService.error("KRITIČNA NAPAKA", `
      Zgodila se je kritična napaka! Incident se je že registriral!<br>
      Sporočite mi svoje podatke preko email-a ali telefona,<br>
      da Vas bom lahko obvestil, ko bo apliakcija spet normalno delovala.<br>
      Za vse nevšečnosti se Vam iskreno opravičujem!<br>
      <br>
      ${msg}
    `)
  }

  uporabniskaNapaka(err: HttpErrorResponse) {
    this.alertService.warn(err.error.info, `Aplikacijo uporabljate na napačen način!<br>Incident se je zabeležil!`)
  }
}
