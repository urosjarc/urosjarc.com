import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpRequest,
  HttpResponse
} from "@angular/common/http";
import {forwardRef, Injectable, Provider} from "@angular/core";
import {Observable, tap} from "rxjs";
import {DbService} from "../../core/services/db/db.service";
import {AlertService} from "../../core/services/alert/alert.service";
import {ApiService} from "../../core/services/api/services";

export const HTTP_INTERCEPTOR_PROVIDER: Provider = {
  provide: HTTP_INTERCEPTORS,
  useExisting: forwardRef(() => HttpInterceptor),
  multi: true
};

@Injectable()
export class HttpInterceptor implements HttpInterceptor {
  static count: number = 0

  constructor(
    private dbService: DbService,
    private alertService: AlertService) {
  }

  // The interceptor does not work nice with @trace
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const self = this
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${this.dbService.token}`
      }
    });
    const count = ++HttpInterceptor.count
    console.log(`${count} REQ `, req)
    return next.handle(req).pipe(
      tap({
        next(ele: any) {
          if (ele instanceof HttpResponse) {
            console.info(`${count} RES `, ele)
          }
          return ele
        },
        error(err: HttpErrorResponse) {
          console.error(`${count} RES `, err)
          if (err.url?.endsWith(ApiService.AuthProfilGetPath)) return
          if (err.status == 0) self.alertService.errorServerNiDostopen(err)
          else if (!err.error || err.status == 404 || err.status >= 500) self.alertService.errorServerNapaka(err)
          else self.alertService.warnUporabniskaNapaka(err)
        }
      }))
  }

}
