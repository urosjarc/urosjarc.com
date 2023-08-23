import {Injectable} from '@angular/core';
import {appUrls} from "../../../app.urls";
import {DobiNastavitveProfilaModel} from "./dobi-nastavitve-profila.model";
import {OsebaTip, UseCase} from "../../../utils/types";
import {ApiService} from "../../services/api/services/api.service";
import {trace} from "../../../utils/trace";

@Injectable()
export class DobiNastavitveProfilaService implements UseCase {

  constructor(private api: ApiService) {
  }

  @trace()
  zdaj(tip: OsebaTip): DobiNastavitveProfilaModel | null {
    switch (tip) {
      case "UCENEC":
        return {
          on_login_url: appUrls.ucenec({}),
          osebni_podatki_observable: this.api.ucenecGet()
        }
      case "UCITELJ":
        return {
          on_login_url: appUrls.ucitelj({}),
          osebni_podatki_observable: this.api.uciteljGet()
        }
      case "ADMIN":
        return {
          on_login_url: appUrls.admin({}),
          osebni_podatki_observable: this.api.adminGet()
        }
      default:
        return null
    }

  }
}
