import {bootstrapApplication} from "@angular/platform-browser";
import {AppComponent} from "./app/routes/app.component";
import {ApiConfiguration} from "./app/core/services/api/api-configuration";
import {provideHttpClient} from "@angular/common/http";
import {MAT_DIALOG_SCROLL_STRATEGY_PROVIDER, MatDialog} from "@angular/material/dialog";
import {MAT_DATE_LOCALE} from "@angular/material/core";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {DATE_PIPE_DEFAULT_OPTIONS} from "@angular/common";
import {LOCALE_ID} from "@angular/core";
import {provideRouter} from "@angular/router";
import routes from "./app/app.routes";
import {locale} from "moment";
import {Dialog, DIALOG_SCROLL_STRATEGY_PROVIDER} from "@angular/cdk/dialog";
import {AlertService} from "./app/core/services/alert/alert.service";
import {DbService} from "./app/core/services/db/db.service";
import {OsebaRepoService} from "./app/core/repos/oseba/oseba-repo.service";
import {AdminRepoService} from "./app/core/repos/admin/admin-repo.service";
import {UcenecRepoService} from "./app/core/repos/ucenec/ucenec-repo.service";
import {UciteljRepoService} from "./app/core/repos/ucitelj/ucitelj-repo.service";
import {
  IzbrisiUporabniskePodatkeService
} from "./app/core/use_cases/izbrisi-uporabniske-podatke/izbrisi-uporabniske-podatke.service";
import {
  SinhronizirajUporabniskePodatkeService
} from "./app/core/use_cases/sinhroniziraj-uporabniske-podatke/sinhroniziraj-uporabniske-podatke.service";
import {PrijaviUporabnikaService} from "./app/core/use_cases/prijavi-uporabnika/prijavi-uporabnika.service";
import {provideAnimations} from "@angular/platform-browser/animations";
import {ApiService} from "./app/core/services/api/services";

bootstrapApplication(AppComponent, {
  providers: [
    DbService,
    ApiService,
    AlertService,
    AdminRepoService,
    OsebaRepoService,
    UcenecRepoService,
    UciteljRepoService,
    IzbrisiUporabniskePodatkeService,
    SinhronizirajUporabniskePodatkeService,
    PrijaviUporabnikaService,
    ApiConfiguration,

    provideAnimations(),
    provideHttpClient(),
    provideRouter(routes),

    Dialog,
    MAT_DIALOG_SCROLL_STRATEGY_PROVIDER,
    DIALOG_SCROLL_STRATEGY_PROVIDER,

    /**
     * Angular material providers
     */
    MatDialog,
    {provide: MAT_DATE_LOCALE, useValue: locale},
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},

    /**
     * Angular material component development kit (CDK)
     */
    {provide: STEPPER_GLOBAL_OPTIONS, useValue: {showError: true}},
    {provide: DATE_PIPE_DEFAULT_OPTIONS, useValue: {dateFormat: 'EEE d.M.YYYY', timezone: '+2'}},

    /**
     * Global providers
     */
    {provide: LOCALE_ID, useValue: locale},
  ],
})
