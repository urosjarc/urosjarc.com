import {LOCALE_ID} from '@angular/core';
import {bootstrapApplication} from '@angular/platform-browser';
import * as moment from "moment"
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {DATE_PIPE_DEFAULT_OPTIONS} from "@angular/common";
import {MAT_DATE_LOCALE} from "@angular/material/core";
import "@angular/common/locales/global/sl"
import 'moment/locale/sl';
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {AppComponent} from "./app/routes/app.component";
import {AlertService} from "./app/core/services/alert/alert.service";
import {HTTP_INTERCEPTOR_PROVIDER} from "./app/middleware/http-interceptor/http-interceptor";

const locale = 'sl'
moment.locale(locale);

bootstrapApplication(AppComponent, {
  providers: [
    AlertService,
    HTTP_INTERCEPTOR_PROVIDER,
    {provide: LOCALE_ID, useValue: locale},
    {provide: MAT_DATE_LOCALE, useValue: locale},
    {provide: STEPPER_GLOBAL_OPTIONS, useValue: {showError: true},},
    {provide: DATE_PIPE_DEFAULT_OPTIONS, useValue: {dateFormat: 'EEE d.M.YYYY', timezone: '+2'}},
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}}
  ]
})
