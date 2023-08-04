import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import * as moment from "moment"

import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {NavGumbComponent} from "./components/nav-gumb/nav-gumb.component";
import {NavBarComponent} from "./components/nav-bar/nav-bar.component";
import {NavStranComponent} from "./components/nav-stran/nav-stran.component";
import {PublicKoledarComponent} from "./routes/public/koledar/public-koledar.component";
import {PublicKontaktComponent} from "./routes/public/kontakt/public-kontakt.component";
import {PublicPrijavaComponent} from "./routes/public/prijava/public-prijava.component";
import {PublicIndexComponent} from "./routes/public/index/public-index.component";
import {AppComponent} from "./routes/app.component";
import {MatExpansionModule} from "@angular/material/expansion";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {InputTelefonComponent} from "./components/input-phone/input-telefon.component";
import {InputEmailComponent} from "./components/input-email/input-email.component";
import {MatInputModule} from "@angular/material/input";
import {InputOsebaComponent} from './components/input-oseba/input-oseba.component';
import {MatTableModule} from "@angular/material/table";
import {InputMsgComponent} from "./components/input-msg/input-msg.component";
import {InputGesloComponent} from "./components/input-geslo/input-geslo.component";
import {HttpClientModule} from "@angular/common/http";
import {AlertService} from "./services/alert/alert.service";
import {MatDialogModule} from "@angular/material/dialog";
import {UcenecComponent} from "./routes/ucenec/ucenec.component";
import {UcenecTestiComponent} from "./routes/ucenec/testi/ucenec-testi.component";
import {UcenecProfilComponent} from "./routes/ucenec/profil/ucenec-profil.component";
import {DateOddaljenostPipe} from "./pipes/DateOddaljenost/date-oddaljenost.pipe";
import {DATE_PIPE_DEFAULT_OPTIONS} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {MatCardModule} from "@angular/material/card";
import {UcenecSporocilaComponent} from "./routes/ucenec/sporocila/ucenec-sporocila.component";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {UcenecTestComponent} from "./routes/ucenec/test/ucenec-test.component";
import {UcenecNalogaComponent} from "./routes/ucenec/naloga/ucenec-naloga.component";
import {MatTabsModule} from "@angular/material/tabs";
import {StatusStylePipe} from "./pipes/StatusClass/status-style.pipe";
import {StoparicaPipe} from "./pipes/Stoparica/stoparica.pipe";
import {TrajanjePipe} from "./pipes/Trajanje/trajanje.pipe";
import {UcenecDeloComponent} from "./routes/ucenec/delo/ucenec-delo.component";
import {PieChartModule} from "@swimlane/ngx-charts";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MAT_DATE_LOCALE, MatNativeDateModule} from "@angular/material/core";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {AuditsTabelaComponent} from "./components/audits-tabela/audits-tabela.component";
import {PublicComponent} from "./routes/public/public.component";
import {DateOddaljenostClassPipe} from "./pipes/DateOddaljenostClass/date-oddaljenost-class.pipe";
import {TableTestiComponent} from "./components/table-testi/table-testi.component";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {LoadingComponent} from "./components/loading/loading.component";
import {ApiService} from "./services/api/openapi/services";
import {AuthService} from "./services/auth/auth.service";
import {DbService} from "./services/db/db.service";
import {SyncService} from "./services/sync/sync.service";
import {API_INTERCEPTOR_PROVIDER, ApiInterceptor} from "./services/api/ApiInterceptor";
import "@angular/common/locales/global/sl"
import 'moment/locale/sl';
import { AdminComponent } from './routes/admin/admin.component';

const locale = 'sl'
moment.locale(locale);

@NgModule({
  declarations: [
    AppComponent,
    NavGumbComponent,
    NavBarComponent,
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
    AuditsTabelaComponent,
    PublicComponent,
    TableTestiComponent,
    LoadingComponent,
    AdminComponent,
  ],
  imports: [
    DateOddaljenostPipe,
    DateOddaljenostClassPipe,
    BrowserModule,
    AppRoutingModule,
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
  ],
  providers: [
    AlertService,
    ApiService,
    AuthService,
    DbService,
    SyncService,
    ApiInterceptor,
    API_INTERCEPTOR_PROVIDER,
    {provide: LOCALE_ID, useValue: locale},
    {provide: MAT_DATE_LOCALE, useValue: locale},
    {provide: DATE_PIPE_DEFAULT_OPTIONS, useValue: {dateFormat: 'EEE d.M.YYYY', timezone: '+2'}},
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}}
  ],
  exports: [],
  bootstrap: [AppComponent]
})

export class AppModule {
}
