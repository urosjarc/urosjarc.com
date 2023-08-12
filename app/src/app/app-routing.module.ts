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
import {UciteljComponent} from "./routes/ucitelj/ucitelj.component";
import {publicPrijavaGuard} from "./guards/prijava/public-prijava.guard";
import {ucenecGuard} from "./guards/ucenec/ucenec.guard";
import {adminGuard} from "./guards/admin/admin.guard";
import {UciteljTestiComponent} from "./routes/ucitelj/testi/ucitelj-testi.component";
import {UciteljSporocilaComponent} from "./routes/ucitelj/sporocila/ucitelj-sporocila.component";
import {UciteljDeloComponent} from "./routes/ucitelj/delo/ucitelj-delo.component";
import {UciteljProfilComponent} from "./routes/ucitelj/profil/ucitelj-profil.component";
import {UciteljUcenciComponent} from "./routes/ucitelj/ucenci/ucitelj-ucenci.component";
import {AdminComponent} from "./routes/admin/admin.component";
import {uciteljGuard} from "./guards/ucitelj/ucitelj.guard";
import {AdminIndexComponent} from "./routes/admin/index/admin-index.component";

const index = route('', {}, {})

const koledar = route('koledar', {}, {})
const kontakt = route('kontakt', {}, {})
const prijava = route('prijava', {}, {})

const profil = route('profil', {}, {})
const delo = route('delo', {}, {})
const sporocila = route('sporocila', {}, {})
const testi = route('testi', {}, {})
const test = route('testi/:test_id', {test_id: stringParser}, {})
const naloga = route('testi/:test_id/naloge/:naloga_id', {test_id: stringParser, naloga_id: stringParser}, {})

const ucenci = route('ucenci', {}, {})

export const routing = {
  "public": route('/', {}, {index, koledar, kontakt, prijava}),
  "ucenec": route('/ucenec', {}, {profil, sporocila, delo, test, testi, naloga}),
  "ucitelj": route('/ucitelj', {}, {profil, ucenci, sporocila, delo, test, testi, naloga}),
  "admin": route('/admin', {}, {index}),
}

const routes = [
  {
    path: routing.public.template, component: PublicComponent, children: [
      {path: index.template, component: PublicIndexComponent},
      {path: koledar.template, component: PublicKoledarComponent},
      {path: kontakt.template, component: PublicKontaktComponent},
      {path: prijava.template, component: PublicPrijavaComponent, canActivate: [publicPrijavaGuard]},
    ]
  },
  {
    path: routing.ucenec.template, canActivate: [ucenecGuard], component: UcenecComponent, children: [
      {path: index.template, component: UcenecTestiComponent},
      {path: testi.template, component: UcenecTestiComponent},
      {path: test.template, component: UcenecTestComponent},
      {path: naloga.template, component: UcenecNalogaComponent},
      {path: sporocila.template, component: UcenecSporocilaComponent},
      {path: delo.template, component: UcenecDeloComponent},
      {path: profil.template, component: UcenecProfilComponent},
    ]
  },
  {path: routing.ucitelj.template, canActivate: [uciteljGuard], component: UciteljComponent, children: [
      {path: index.template, component: UciteljTestiComponent},
      {path: testi.template, component: UciteljTestiComponent},
      {path: ucenci.template, component: UciteljUcenciComponent},
      {path: sporocila.template, component: UciteljSporocilaComponent},
      {path: delo.template, component: UciteljDeloComponent},
      {path: profil.template, component: UciteljProfilComponent},
    ]},
  {path: routing.admin.template, canActivate: [adminGuard], component: AdminComponent, children: [
      {path: index.template, component: AdminIndexComponent},
    ]},
  {path: '**', component: PublicIndexComponent},
];

for (const route of routes) route.path = route.path.replaceAll("/", "")

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
