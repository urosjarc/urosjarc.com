import {PublicKoledarComponent} from "./routes/public/koledar/public-koledar.component";
import {PublicKontaktComponent} from "./routes/public/kontakt/public-kontakt.component";
import {PublicPrijavaComponent} from "./routes/public/prijava/public-prijava.component";
import {PublicIndexComponent} from "./routes/public/index/public-index.component";
import {PublicComponent} from "./routes/public/public.component";
import {route, stringParser} from "typesafe-routes";
import {UcenecProfilComponent} from "./routes/ucenec/profil/ucenec-profil.component";
import {UcenecComponent} from "./routes/ucenec/ucenec.component";
import {UcenecSporocilaComponent} from "./routes/ucenec/sporocila/ucenec-sporocila.component";
import {UcenecDeloComponent} from "./routes/ucenec/delo/ucenec-delo.component";
import {UcenecTestComponent} from "./routes/ucenec/test/ucenec-test.component";
import {UcenecNalogaComponent} from "./routes/ucenec/naloga/ucenec-naloga.component";
import {UcenecTestiComponent} from "./routes/ucenec/testi/ucenec-testi.component";
import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";

const index = route('', {}, {})

const koledar = route('koledar', {}, {})
const kontakt = route('kontakt', {}, {})
const prijava = route('prijava', {}, {})

const delo = route('prijava', {}, {})
const naloga = route('testi/:test_id/naloge/:naloga_id', {test_id: stringParser, naloga_id: stringParser}, {})
const profil = route('koledar', {}, {})
const sporocila = route('kontakt', {}, {})
const test = route('testi/:test_id', {test_id: stringParser}, {})
const testi = route('testi', {}, {})

export const routing = {
  "public": route('/', {}, {index, koledar, kontakt, prijava}),
  "ucenec": route('/ucenec', {}, {delo, naloga, profil, sporocila, test, testi}),
}

const routes = [
  {
    path: routing.public.template, component: PublicComponent, children: [
      {path: index.template, component: PublicIndexComponent},
      {path: koledar.template, component: PublicKoledarComponent},
      {path: kontakt.template, component: PublicKontaktComponent},
      {path: prijava.template, component: PublicPrijavaComponent},
    ]
  },
  {
    path: routing.ucenec.template, component: UcenecComponent, children: [
      {path: index.template, component: UcenecTestiComponent},
      {path: delo.template, component: UcenecDeloComponent},
      {path: naloga.template, component: UcenecNalogaComponent},
      {path: profil.template, component: UcenecProfilComponent},
      {path: sporocila.template, component: UcenecSporocilaComponent},
      {path: test.template, component: UcenecTestComponent},
      {path: testi.template, component: UcenecTestiComponent},
    ]
  },
  {path: '**', component: PublicIndexComponent},
];

for (const route of routes) route.path = route.path.replaceAll("/", "")

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
