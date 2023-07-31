import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {NavGumbComponent} from "./components/nav-gumb/nav-gumb.component";
import {NavBarComponent} from "./components/nav-bar/nav-bar.component";
import {NavStranComponent} from "./components/nav-stran/nav-stran.component";
import {KoledarComponent} from "./routes/public/koledar/koledar.component";
import {KontaktComponent} from "./routes/public/kontakt/kontakt.component";
import {PrijavaComponent} from "./routes/public/prijava/prijava.component";
import {PublicComponent} from "./routes/public/index/public.component";
import {RootComponent} from "./routes/root.component";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {InputTelefonComponent} from "./components/input-phone/input-telefon.component";
import {InputEmailComponent} from "./components/input-email/input-email.component";
import {MatInputModule} from "@angular/material/input";
import {InputOsebaComponent} from './components/input-oseba/input-oseba.component';
import {MatTableModule} from "@angular/material/table";
import {InputMsgComponent} from "./components/input-msg/input-msg.component";
import {InputGesloComponent} from "./components/input-geslo/input-geslo.component";
import {DefaultService} from "./api";
import {HttpClientModule} from "@angular/common/http";
import {AlertService} from "./components/alert/alert.service";
import {MatDialogModule} from "@angular/material/dialog";
import {UcenecComponent} from "./routes/ucenec/ucenec.component";
import {TestiComponent} from "./routes/ucenec/testi/testi.component";
import {ProfilComponent} from "./routes/ucenec/profil/profil.component";
import {DateOddaljenostPipe} from "./pipes/DateOddaljenost/date-oddaljenost.pipe";
import {DATE_PIPE_DEFAULT_OPTIONS} from "@angular/common";
import "@angular/common/locales/global/sl"
import {MatListModule} from "@angular/material/list";
import {MatCardModule} from "@angular/material/card";
import {SporocilaComponent} from "./routes/ucenec/sporocila/sporocila.component";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {TestComponent} from "./routes/ucenec/test/test.component";
import {StatusClassPipe} from "./pipes/StatusClass/status-class.pipe";


@NgModule({
  declarations: [
    RootComponent,
    NavGumbComponent,
    NavBarComponent,
    NavStranComponent,
    KoledarComponent,
    KontaktComponent,
    PrijavaComponent,
    PublicComponent,
    InputEmailComponent,
    InputTelefonComponent,
    InputOsebaComponent,
    InputMsgComponent,
    InputGesloComponent,
    UcenecComponent,
    ProfilComponent,
    TestiComponent,
    DateOddaljenostPipe,
    SporocilaComponent,
    TestComponent,
    StatusClassPipe
  ],
  imports: [
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
    MatSortModule
  ],
  providers: [
    DefaultService,
    AlertService,
    {provide: LOCALE_ID, useValue: 'sl'},
    {provide: DATE_PIPE_DEFAULT_OPTIONS, useValue: {dateFormat: 'EEE d.M.YYYY', timezone: '+2'}},
  ],
  bootstrap: [RootComponent]
})
export class AppModule {
}
