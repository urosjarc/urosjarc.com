import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {NavGumbComponent} from "./components/nav-gumb/nav-gumb.component";
import {NavBarComponent} from "./components/nav-bar/nav-bar.component";
import {NavStranComponent} from "./components/nav-stran/nav-stran.component";
import {KoledarComponent} from "./routes/public/koledar/koledar.component";
import {KontaktComponent} from "./routes/public/kontakt/kontakt.component";
import {PrijavaComponent} from "./routes/public/prijava/prijava.component";
import {PublicComponent} from "./routes/public/public.component";

@NgModule({
  declarations: [
    AppComponent,
    NavGumbComponent,
    NavBarComponent,
    NavStranComponent,
    KoledarComponent,
    KontaktComponent,
    PrijavaComponent,
    PublicComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
