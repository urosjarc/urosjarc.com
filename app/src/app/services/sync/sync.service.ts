import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {DbService} from "../db/db.service";
import {ApiService} from "../api/openapi/services/api.service";
import {trace} from "../../utils";
import {SyncProfil} from "./SyncArgs";
import {AlertService} from "../alert/alert.service";

@Injectable({providedIn: 'root'})
export class SyncService {
  constructor(
    private alertSerivce: AlertService,
    private apiService: ApiService,
    private dbService: DbService) {
  }

  @trace()
  sync(args: SyncProfil) {
    const self = this;
    let observer: Observable<any>
    if (args.profil.tip?.includes("UCENEC"))
      observer = this.apiService.ucenecGet({})
    else if (args.profil.tip?.includes("ADMIN"))
      observer = this.apiService.adminGet({})
    else if (args.profil.tip?.includes("UCITELJ"))
      observer = this.apiService.uciteljGet({})
    else {
      return args.error()
    }

    return observer.subscribe({
      async next(value) {
        await self.dbService.reset(value)
        args.next()
      },
      error() {
        args.error()
      }
    })

  }

  @trace()
  pocisti() {
    this.dbService.delete()
    this.dbService.set_token("")
    this.dbService.set_root_id("")
  }
}
