import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {forwardRef, Injectable, Provider} from "@angular/core";
import {DbService} from "./services/db/db.service";
import {AlertService} from "./services/alert/alert.service";
import {ApiService} from "./services/api/openapi/services";
import {trace} from "./utils";
import {Observable, tap} from "rxjs";

export const API_INTERCEPTOR_PROVIDER: Provider = {
  provide: HTTP_INTERCEPTORS,
  useExisting: forwardRef(() => Interceptor),
  multi: true
};

@Injectable()
export class Interceptor implements HttpInterceptor {
  constructor(
    private dbService: DbService,
    private alertService: AlertService) {
  }

  // The interceptor does not work nice with @trace
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const self = this
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${this.dbService.get_token()}`
      }
    });
    console.log("REQ ", req)
    return next.handle(req).pipe(
      tap({
        next: self.odgovor,
        error(err: HttpErrorResponse) {
          if (err.url?.endsWith(ApiService.AuthProfilGetPath)) return

          if (err.status == 0) self.serverNiDostopen(err)
          else if (!err.error || err.status == 404 || err.status >= 500) self.serverNapaka(err)
          else self.uporabniskaNapaka(err)

        }
      }))
  }

  @trace()
  odgovor(ele: any) {
    return ele
  }

  @trace()
  serverNiDostopen(err: HttpErrorResponse) {
    const msg = err.message
    this.alertService.error("SERVER NEDOSTOPEN", `
      Komunikacija z serverjem ni bila vspostavljena! Incident se ni mogel registrirati!<br>
      Preverite internetno povezavo in če je vspostavljena, mi nujno posredujte vse informacije<br>
      o napaki preko telefonskega klica, ali email-a v primeru nedoseglivosti.<br>
      Za vse nevšečnosti se Vam iskreno opravičujem!<br>
      <br>
      ${msg}
    `)
  }

  @trace()
  serverNapaka(err: HttpErrorResponse) {
    const msg = err.message
    this.alertService.error("KRITIČNA NAPAKA", `
      Zgodila se je kritična napaka! Incident se je registriral!<br>
      Če želite da se Vas obvesti, ko bo napaka rešena, mi posredujte vašo željo preko email-a ali sms-a.<br>
      Za vse nevšečnosti se Vam iskreno opravičujem!<br>
      <br>
      ${msg}
    `)
  }

  @trace()
  uporabniskaNapaka(err: HttpErrorResponse) {
    this.alertService.warn(err.error.info, `
      Aplikacijo uporabljate na napačen način.<br>
      Incident se je zabeležil!
    `)
  }
}
