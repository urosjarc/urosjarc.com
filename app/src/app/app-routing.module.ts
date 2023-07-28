import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {KoledarComponent} from "./routes/public/koledar/koledar.component";
import {KontaktComponent} from "./routes/public/kontakt/kontakt.component";
import {PrijavaComponent} from "./routes/public/prijava/prijava.component";
import {PublicComponent} from "./routes/public/index/public.component";
import {TestiComponent} from "./routes/ucenec/testi/testi.component";
import {ProfilComponent} from "./routes/ucenec/profil/profil.component";
import {UcenecComponent} from "./routes/ucenec/ucenec.component";

const routes: Routes = [
  {path: '', component: PublicComponent},
  {path: 'koledar', component: KoledarComponent},
  {path: 'kontakt', component: KontaktComponent},
  {path: 'prijava', component: PrijavaComponent},
  {
    path: 'ucenec', component: UcenecComponent, children: [
      {path: 'profil', component: ProfilComponent},
      {path: 'testi', component: TestiComponent},
    ]
  },
  {path: '**', component: PublicComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
