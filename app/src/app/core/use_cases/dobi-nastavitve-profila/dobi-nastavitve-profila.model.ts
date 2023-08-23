import {Observable} from "rxjs";
import {OsebaData} from "../../services/api/models/oseba-data";
import {AppUrl} from "../../../utils/types";

export interface DobiNastavitveProfilaModel {
  on_login_url: AppUrl
  osebni_podatki_observable: Observable<OsebaData>
}
