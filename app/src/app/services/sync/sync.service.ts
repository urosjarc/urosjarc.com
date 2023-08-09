import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Profil} from "../api/openapi/models/profil";
import {OsebaData} from "../api/openapi/models/oseba-data";
import {DbService} from "../db/db.service";
import {ApiService} from "../api/openapi/services/api.service";
import {trace} from "../../utils";

@Injectable({providedIn: 'root'})
export class SyncService {
  constructor(
    private apiService: ApiService,
    private dbService: DbService) {
  }

  @trace()
  sync(profil: Profil, result: (osebaData: OsebaData) => void, fail: () => void) {
    const self = this;
    let observer: Observable<any>
    if (profil.tip?.includes("UCENEC"))
      observer = this.apiService.ucenecGet({})
    else if (profil.tip?.includes("ADMIN"))
      observer = this.apiService.adminGet({})
    else {
      return fail()
    }

    return observer.subscribe({
      next(value) {
        self.dbService.reset(value).then(() => {
          result(value)
        }).catch(() => {
          fail()
        })
      },
      error() {
        fail()
      }
    })

  }
}
