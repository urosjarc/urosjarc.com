import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {DbService} from "../db/db.service";
import {ApiService} from "../api/openapi/services/api.service";
import {trace} from "../../utils";
import {SyncOsebaData} from "./SyncArgs";
import {AlertService} from "../alert/alert.service";

@Injectable({providedIn: 'root'})
export class SyncService {
  constructor(
    private alertSerivce: AlertService,
    private apiService: ApiService,
    private dbService: DbService) {
  }

  @trace()
  osebaData(args: SyncOsebaData) {
    const self = this;
    let observer: Observable<any>
    if (args.profil.tip?.includes("UCENEC"))
      observer = this.apiService.ucenecGet({})
    else if (args.profil.tip?.includes("ADMIN"))
      observer = this.apiService.adminGet({})
    else {
      return args.error()
    }

    return observer.subscribe({
      next(value) {
        self.dbService.reset(value).then(() => {
          args.next()
        }).catch((err) => {
          self.alertSerivce.sinhronizacijskaNapaka(err)
          args.error()
        })
      },
      error() {
        args.error()
      }
    })

  }
}
