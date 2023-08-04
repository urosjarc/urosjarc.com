import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {forwardRef, Injectable, Provider} from "@angular/core";
import {AlertService} from "../alert/alert.service";
import {ApiService} from "./openapi/services";
import {DbService} from "../db/db.service";

export const API_INTERCEPTOR_PROVIDER: Provider = {
  provide: HTTP_INTERCEPTORS,
  useExisting: forwardRef(() => ApiInterceptor),
  multi: true
};

@Injectable()
export class ApiInterceptor implements HttpInterceptor {
  constructor(
    private dbService: DbService,
    private alertService: AlertService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${this.dbService.get_token()}`
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
