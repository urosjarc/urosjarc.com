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
import {provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {middleware_interceptors} from "./middleware";
import {MatNativeDateModule} from "@angular/material/core";
import {importProvidersFrom} from "@angular/core";
import {ui_pipes} from "./ui/pipes";


export const angular_providers = [
  Dialog,
  MatDialog,
  provideAnimations(),
  provideRouter(appRoutes),
  importProvidersFrom(MatNativeDateModule),
  provideHttpClient(withInterceptorsFromDi()),
]

export const core_providers = [
  ...ui_pipes,
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
