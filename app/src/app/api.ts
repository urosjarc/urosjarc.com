import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {db} from "./db";
import {Injectable} from "@angular/core";
import {AlertService} from "./components/alert/alert.service";

@Injectable()
export class ApiInterceptor implements HttpInterceptor {
  alertService: AlertService

  constructor(alertService: AlertService) {
    this.alertService = alertService
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const self = this
    // Apply the headers
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${db.get_token()}`
      }
    });
    return next.handle(req).pipe(
      tap(x => x, (err: HttpErrorResponse) => {
        // Handle this err
        console.error({err})
        if (err.status == 404 || err.status >= 500) this.alertService.error(err.message)
        else this.alertService.warn(err.message)
      })
    );
  }
}
