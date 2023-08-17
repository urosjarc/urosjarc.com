import {PublicKoledarComponent} from "./routes/public/koledar/public-koledar.component";
import {PublicKontaktComponent} from "./routes/public/kontakt/public-kontakt.component";
import {PublicPrijavaComponent} from "./routes/public/prijava/public-prijava.component";
import {PublicIndexComponent} from "./routes/public/index/public-index.component";
import {PublicComponent} from "./routes/public/public.component";
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
import {UciteljTestiComponent} from "./routes/ucitelj/testi/ucitelj-testi.component";
import {UciteljSporocilaComponent} from "./routes/ucitelj/sporocila/ucitelj-sporocila.component";
import {UciteljDeloComponent} from "./routes/ucitelj/delo/ucitelj-delo.component";
import {UciteljProfilComponent} from "./routes/ucitelj/profil/ucitelj-profil.component";
import {UciteljUcenciComponent} from "./routes/ucitelj/ucenci/ucitelj-ucenci.component";
import {AdminComponent} from "./routes/admin/admin.component";
import {AdminIndexComponent} from "./routes/admin/index/admin-index.component";
import {UciteljZvezkiComponent} from "./routes/ucitelj/zvezki/ucitelj-zvezki.component";
import {authCheckGuard} from "./utils/guards/auth-check/auth-check.guard";
import {autoLoginGuard} from "./utils/guards/auto-login/public-prijava.guard";
import {paths, routes} from "./routes"

const appRoutes = [
  {
    path: routes.public.template,
    component: PublicComponent,
    children: [
      {path: paths.index.template, component: PublicIndexComponent},
      {path: paths.koledar.template, component: PublicKoledarComponent},
      {path: paths.kontakt.template, component: PublicKontaktComponent},
      {
        path: paths.prijava.template,
        canActivate: [autoLoginGuard({routeFn: routes.profil_tip})],
        component: PublicPrijavaComponent
      },
    ]
  },
  {
    path: routes.ucenec.template,
    canActivate: [authCheckGuard({tip: "UCENEC", error_redirect: routes.public({}).prijava({})})],
    component: UcenecComponent,
    children: [
      {path: paths.index.template, component: UcenecTestiComponent},
      {path: paths.testi.template, component: UcenecTestiComponent},
      {path: paths.test.template, component: UcenecTestComponent},
      {path: paths.naloga.template, component: UcenecNalogaComponent},
      {path: paths.sporocila.template, component: UcenecSporocilaComponent},
      {path: paths.delo.template, component: UcenecDeloComponent},
      {path: paths.profil.template, component: UcenecProfilComponent},
    ]
  },
  {
    path: routes.ucitelj.template,
    canActivate: [authCheckGuard({tip: "UCITELJ", error_redirect: routes.public({}).prijava({})})],
    component: UciteljComponent,
    children: [
      {path: paths.index.template, component: UciteljTestiComponent},
      {path: paths.zvezki.template, component: UciteljZvezkiComponent},
      {path: paths.testi.template, component: UciteljTestiComponent},
      {path: paths.ucenci.template, component: UciteljUcenciComponent},
      {path: paths.sporocila.template, component: UciteljSporocilaComponent},
      {path: paths.delo.template, component: UciteljDeloComponent},
      {path: paths.profil.template, component: UciteljProfilComponent},
    ]
  },
  {
    path: routes.admin.template,
    canActivate: [authCheckGuard({tip: "ADMIN", error_redirect: routes.public({}).prijava({})})],
    component: AdminComponent,
    children: [
      {path: paths.index.template, component: AdminIndexComponent},
    ]
  },
  {path: '**', component: PublicIndexComponent},
];

for (const route of appRoutes) route.path = route.path.replaceAll("/", "")

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class Routing {
}
