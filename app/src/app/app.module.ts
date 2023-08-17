import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import * as moment from "moment"

import {LoggerModule, NgxLoggerLevel} from "ngx-logger"
import {Routing} from './router.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {PublicKoledarComponent} from "./routes/public/koledar/public-koledar.component";
import {PublicKontaktComponent} from "./routes/public/kontakt/public-kontakt.component";
import {PublicPrijavaComponent} from "./routes/public/prijava/public-prijava.component";
import {PublicIndexComponent} from "./routes/public/index/public-index.component";
import {AppComponent} from "./routes/app.component";
import {MatExpansionModule} from "@angular/material/expansion";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";
import {HttpClientModule} from "@angular/common/http";
import {MatDialogModule} from "@angular/material/dialog";
import {UcenecComponent} from "./routes/ucenec/ucenec.component";
import {UcenecTestiComponent} from "./routes/ucenec/testi/ucenec-testi.component";
import {UcenecProfilComponent} from "./routes/ucenec/profil/ucenec-profil.component";
import {DATE_PIPE_DEFAULT_OPTIONS, NgFor} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {MatCardModule} from "@angular/material/card";
import {UcenecSporocilaComponent} from "./routes/ucenec/sporocila/ucenec-sporocila.component";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {UcenecTestComponent} from "./routes/ucenec/test/ucenec-test.component";
import {UcenecNalogaComponent} from "./routes/ucenec/naloga/ucenec-naloga.component";
import {MatTabsModule} from "@angular/material/tabs";
import {UcenecDeloComponent} from "./routes/ucenec/delo/ucenec-delo.component";
import {PieChartModule} from "@swimlane/ngx-charts";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MAT_DATE_LOCALE, MatNativeDateModule} from "@angular/material/core";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {PublicComponent} from "./routes/public/public.component";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import "@angular/common/locales/global/sl"
import 'moment/locale/sl';
import {UciteljComponent} from './routes/ucitelj/ucitelj.component';
import {UciteljUcenciComponent} from "./routes/ucitelj/ucenci/ucitelj-ucenci.component";
import {UciteljTestiComponent} from "./routes/ucitelj/testi/ucitelj-testi.component";
import {UciteljSporocilaComponent} from "./routes/ucitelj/sporocila/ucitelj-sporocila.component";
import {UciteljDeloComponent} from "./routes/ucitelj/delo/ucitelj-delo.component";
import {UciteljProfilComponent} from "./routes/ucitelj/profil/ucitelj-profil.component";
import {AdminComponent} from "./routes/admin/admin.component";
import {AdminIndexComponent} from "./routes/admin/index/admin-index.component";
import {CdkDrag, CdkDropList} from "@angular/cdk/drag-drop";
import {UciteljZvezkiComponent} from "./routes/ucitelj/zvezki/ucitelj-zvezki.component";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {MatStepperModule} from "@angular/material/stepper";
import {ButtonToolbarComponent} from "./ui/parts/buttons/button-toolbar/button-toolbar.component";
import {NavStranComponent} from "./ui/parts/cards/card-navigacija/nav-stran.component";
import {ToolbarNavigacijaComponent} from "./ui/parts/toolbars/toolbar-navigacija/toolbar-navigacija.component";

const locale = 'sl'
moment.locale(locale);

const loggerModule = LoggerModule.forRoot({
  serverLoggingUrl: '/api/logs',
  level: NgxLoggerLevel.DEBUG,
  serverLogLevel: NgxLoggerLevel.ERROR
})

@NgModule({
  declarations: [
    AppComponent,
    ButtonToolbarComponent,
    ToolbarNavigacijaComponent,
    NavStranComponent,
    PublicKoledarComponent,
    PublicKontaktComponent,
    PublicPrijavaComponent,
    PublicIndexComponent,
    InputEmailComponent,
    InputTelefonComponent,
    InputOsebaComponent,
    InputMsgComponent,
    InputGesloComponent,
    UcenecComponent,
    UcenecProfilComponent,
    UcenecTestiComponent,
    UcenecSporocilaComponent,
    UcenecTestComponent,
    StatusStylePipe,
    UcenecNalogaComponent,
    StoparicaPipe,
    TrajanjePipe,
    UcenecDeloComponent,
    TableAuditsComponent,
    PublicComponent,
    TableTestiComponent,
    ProgressBarComponent,
    UciteljComponent,
    UciteljUcenciComponent,
    UciteljTestiComponent,
    UciteljSporocilaComponent,
    UciteljDeloComponent,
    UciteljProfilComponent,
    AdminComponent,
    AdminIndexComponent,
    TableOsebeComponent,
    TableOsebeComponent,
    TableSporocilaComponent,
    PrikaziProfilOsebeComponent,
    UciteljZvezkiComponent,
    TableNalogeComponent,
    TableTematikeComponent,
    TableZvezkiComponent,
    TableUcenciComponent
  ],
  imports: [
    CdkDropList, NgFor, CdkDrag,
    DateOddaljenostPipe,
    DateOddaljenostClassPipe,
    BrowserModule,
    Routing,
    BrowserAnimationsModule,
    MatIconModule,
    MatButtonModule,
    MatExpansionModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatTableModule,
    HttpClientModule,
    MatDialogModule,
    MatListModule,
    MatCardModule,
    MatPaginatorModule,
    MatSortModule,
    MatTabsModule,
    PieChartModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonToggleModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    ApiModule,
    loggerModule,
    MatStepperModule
  ],
  providers: [
    AlertService,
    AuthService,
    DbService,
    SyncService,
    ApiInterceptor,
    API_INTERCEPTOR_PROVIDER,
    {provide: LOCALE_ID, useValue: locale},
    {provide: MAT_DATE_LOCALE, useValue: locale},
    {provide: STEPPER_GLOBAL_OPTIONS, useValue: {showError: true},},
    {provide: DATE_PIPE_DEFAULT_OPTIONS, useValue: {dateFormat: 'EEE d.M.YYYY', timezone: '+2'}},
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}}
  ],
  exports: [],
  bootstrap: [AppComponent]
})

export class AppModule {
}
