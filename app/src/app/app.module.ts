import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import { NavGumbComponent } from './nav-gumb/nav-gumb.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { NavStranComponent } from './nav-stran/nav-stran.component';

@NgModule({
  declarations: [
    AppComponent,
    NavGumbComponent,
    NavBarComponent,
    NavStranComponent
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
