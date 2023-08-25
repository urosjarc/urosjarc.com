import {AlertService} from "./alert/alert.service";
import {DbService} from "./db/db.service";
import {ApiConfiguration} from "./api/api-configuration";
import {environment} from "../../../environments/environment";
import {ApiService} from "./api/services";
import {WindowService} from "./window/window.service";

export const core_services = [
  AlertService,
  ApiService,
  DbService,
  WindowService,

  {provide: ApiConfiguration, useValue: {rootUrl: environment.rootUrl} as ApiConfiguration},
]
