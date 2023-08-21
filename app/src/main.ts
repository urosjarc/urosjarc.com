/// <reference types="@angular/localize" />

import * as moment from "moment"
import {bootstrapApplication} from "@angular/platform-browser";
import {AppComponent} from "./app/routes/app.component";
import {
  angular_configuration,
  angular_material_configuration,
  angular_providers,
  core_providers,
  locale
} from "./app/app.providers";

moment.locale(locale);

bootstrapApplication(AppComponent, {
  providers: [
    ...angular_providers,
    ...core_providers,
    ...angular_configuration,
    ...angular_material_configuration
  ]
})
