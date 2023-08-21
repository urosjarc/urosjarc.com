import {provideAnimations} from "@angular/platform-browser/animations";
import {provideRouter} from "@angular/router";
import routes from "./app.routes";
import {core_repos} from "./core/repos";
import {core_services} from "./core/services";
import {core_useCases} from "./core/use_cases";
import {Dialog, DIALOG_SCROLL_STRATEGY_PROVIDER} from "@angular/cdk/dialog";
import {MAT_DIALOG_SCROLL_STRATEGY_PROVIDER, MatDialog} from "@angular/material/dialog";
import {MAT_DATE_LOCALE} from "@angular/material/core";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {DATE_PIPE_DEFAULT_OPTIONS} from "@angular/common";
import {LOCALE_ID} from "@angular/core";
import {provideHttpClient} from "@angular/common/http";

export const locale = 'sl'

export const angular_providers = [
  Dialog,
  MatDialog,
  provideHttpClient(),
  provideAnimations(),
  provideRouter(routes),
]

export const core_providers = [
  ...core_repos,
  ...core_services,
  ...core_useCases,
]

export const angular_configuration = [
  {provide: LOCALE_ID, useValue: locale},
  {provide: DATE_PIPE_DEFAULT_OPTIONS, useValue: {dateFormat: 'EEE d.M.YYYY', timezone: '+2'}},
]

export const angular_material_configuration = [
  DIALOG_SCROLL_STRATEGY_PROVIDER,
  MAT_DIALOG_SCROLL_STRATEGY_PROVIDER,
  {provide: MAT_DATE_LOCALE, useValue: locale},
  {provide: STEPPER_GLOBAL_OPTIONS, useValue: {showError: true}},
  {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},
]
