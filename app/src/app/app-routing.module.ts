import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PublicKoledarComponent} from "./routes/public/koledar/public-koledar.component";
import {PublicKontaktComponent} from "./routes/public/kontakt/public-kontakt.component";
import {PublicPrijavaComponent} from "./routes/public/prijava/public-prijava.component";
import {PublicIndexComponent} from "./routes/public/index/public-index.component";
import {UcenecTestiComponent} from "./routes/ucenec/testi/ucenec-testi.component";
import {UcenecProfilComponent} from "./routes/ucenec/profil/ucenec-profil.component";
import {UcenecComponent} from "./routes/ucenec/ucenec.component";
import {UcenecSporocilaComponent} from "./routes/ucenec/sporocila/ucenec-sporocila.component";
import {UcenecTestComponent} from "./routes/ucenec/test/ucenec-test.component";
import {UcenecNalogaComponent} from "./routes/ucenec/naloga/ucenec-naloga.component";
import {UcenecZgodovinaComponent} from "./routes/ucenec/zgodovina/ucenec-zgodovina.component";
import {PublicComponent} from "./routes/public/public.component";

const routes: Routes = [
  {
    path: '', component: PublicComponent, children: [
      {path: '', component: PublicIndexComponent},
      {path: 'koledar', component: PublicKoledarComponent},
      {path: 'kontakt', component: PublicKontaktComponent},
      {path: 'prijava', component: PublicPrijavaComponent},
    ]
  },
  {
    path: 'ucenec', component: UcenecComponent, children: [
      {path: '', component: UcenecTestiComponent},
      {path: 'profil', component: UcenecProfilComponent},
      {path: 'sporocila', component: UcenecSporocilaComponent},
      {path: 'zgodovina', component: UcenecZgodovinaComponent},
      {path: 'testi/:test_id', component: UcenecTestComponent},
      {path: 'testi/:test_id/naloge/:naloga_id', component: UcenecNalogaComponent}
    ]
  },
  {path: '**', component: PublicIndexComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
