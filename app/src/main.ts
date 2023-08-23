/// <reference types="@angular/localize" />

import * as moment from "moment"
import {bootstrapApplication} from "@angular/platform-browser";
import {AppComponent} from "./app/routes/app.component";
import {
  angular_configuration,
  angular_material_configuration,
  angular_providers,
  core_providers,
} from "./app/app.providers";
import {LoggerModule, NgxLoggerLevel} from "ngx-logger";

moment.locale($localize.locale);

const logger_providers = LoggerModule.forRoot({level: NgxLoggerLevel.DEBUG}).providers || []

bootstrapApplication(AppComponent, {
  providers: [
    ...logger_providers,
    ...angular_providers,
    ...core_providers,
    ...angular_configuration,
    ...angular_material_configuration
  ]
})
