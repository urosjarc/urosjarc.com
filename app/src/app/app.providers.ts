import {provideAnimations} from "@angular/platform-browser/animations";
import {provideRouter} from "@angular/router";
import appRoutes from "./app.routes";
import {core_repos} from "./core/repos";
import {core_services} from "./core/services";
import {core_useCases} from "./core/use_cases";
import {Dialog, DIALOG_SCROLL_STRATEGY_PROVIDER} from "@angular/cdk/dialog";
import {MAT_DIALOG_SCROLL_STRATEGY_PROVIDER, MatDialog} from "@angular/material/dialog";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {DATE_PIPE_DEFAULT_OPTIONS} from "@angular/common";
import {LOCALE_ID} from "@angular/core";
import {provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {middleware_interceptors} from "./middleware";

export const locale = 'sl'

export const angular_providers = [
  Dialog,
  MatDialog,
  provideHttpClient(withInterceptorsFromDi()),
  provideAnimations(),
  provideRouter(appRoutes),
]

export const core_providers = [
  ...core_repos,
  ...core_services,
  ...core_useCases,
  ...middleware_interceptors
]

export const angular_configuration = [
  {provide: DATE_PIPE_DEFAULT_OPTIONS, useValue: {dateFormat: 'EEE d.M.YYYY', timezone: '+2'}},
]

export const angular_material_configuration = [
  DIALOG_SCROLL_STRATEGY_PROVIDER,
  MAT_DIALOG_SCROLL_STRATEGY_PROVIDER,
  {provide: STEPPER_GLOBAL_OPTIONS, useValue: {showError: true}},
  {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},
]
