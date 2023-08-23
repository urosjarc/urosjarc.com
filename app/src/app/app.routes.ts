import {appUrls, paths} from "./app.urls"
import {authCheckGuard} from "./middleware/guards/auth-check/auth-check.guard";
import {Route} from "@angular/router";
import {autoLoginGuard} from "./middleware/guards/auto-login/auto-login";

const public_import = () => import('./routes/public/public.component').then(c => c.PublicComponent)

const appRoutes: Route[] = [
  {
    path: appUrls.public.template,
    loadComponent: public_import,
    children: [
      {
        path: paths.index.template,
        loadComponent: () => import('./routes/public/index/public-index.component').then(c => c.PublicIndexComponent)
      },
      {
        path: paths.koledar.template,
        loadComponent: () => import('./routes/public/koledar/public-koledar.component').then(c => c.PublicKoledarComponent)
      },
      {
        path: paths.kontakt.template,
        loadComponent: () => import('./routes/public/kontakt/public-kontakt.component').then(c => c.PublicKontaktComponent)
      },
      {
        path: paths.prijava.template,
        canActivate: [autoLoginGuard],
        loadComponent: () => import('./routes/public/prijava/public-prijava.component').then(c => c.PublicPrijavaComponent),
      },
    ]
  },
  {
    path: appUrls.ucenec.template,
    canActivate: [authCheckGuard({tip: "UCENEC", error_redirect: appUrls.public({}).prijava({})})],
    loadComponent: () => import('./routes/ucenec/ucenec.component').then(c => c.UcenecComponent),
    children: [
      {
        path: paths.index.template,
        loadComponent: () => import('./routes/ucenec/testi/ucenec-testi.component').then(c => c.UcenecTestiComponent)
      },
      {
        path: paths.testi.template,
        loadComponent: () => import('./routes/ucenec/testi/ucenec-testi.component').then(c => c.UcenecTestiComponent)
      },
      {
        path: paths.test.template,
        loadComponent: () => import('./routes/ucenec/testi/test/ucenec-testi-test.component').then(c => c.UcenecTestiTestComponent)
      },
      {
        path: paths.naloga.template,
        loadComponent: () => import('./routes/ucenec/testi/test/naloga/ucenec-testi-test-naloga.component').then(c => c.UcenecTestiTestNalogaComponent)
      },
      {
        path: paths.sporocila.template,
        loadComponent: () => import('./routes/ucenec/sporocila/ucenec-sporocila.component').then(c => c.UcenecSporocilaComponent)
      },
      {
        path: paths.delo.template,
        loadComponent: () => import('./routes/ucenec/delo/ucenec-delo.component').then(c => c.UcenecDeloComponent)
      },
      {
        path: paths.profil.template,
        loadComponent: () => import('./routes/ucenec/profil/ucenec-profil.component').then(c => c.UcenecProfilComponent),
      },
    ]
  },
  {
    path: appUrls.ucitelj.template,
    canActivate: [authCheckGuard({tip: "UCITELJ", error_redirect: appUrls.public({}).prijava({})})],
    loadComponent: () => import('./routes/ucitelj/ucitelj.component').then(c => c.UciteljComponent),
    children: [
      {
        path: paths.index.template,
        loadComponent: () => import('./routes/ucitelj/testi/ucitelj-testi.component').then(c => c.UciteljTestiComponent),
      },
      {
        path: paths.zvezki.template,
        loadComponent: () => import('./routes/ucitelj/ucitelj.component').then(c => c.UciteljComponent),
      },
      {
        path: paths.testi.template,
        loadComponent: () => import('./routes/ucitelj/testi/ucitelj-testi.component').then(c => c.UciteljTestiComponent),
      },
      {
        path: paths.ucenci.template,
        loadComponent: () => import('./routes/ucitelj/ucenci/ucitelj-ucenci.component').then(c => c.UciteljUcenciComponent),
      },
      {
        path: paths.sporocila.template,
        loadComponent: () => import('./routes/ucitelj/sporocila/ucitelj-sporocila.component').then(c => c.UciteljSporocilaComponent),
      },
      {
        path: paths.delo.template,
        loadComponent: () => import('./routes/ucitelj/delo/ucitelj-delo.component').then(c => c.UciteljDeloComponent),
      },
      {
        path: paths.profil.template,
        loadComponent: () => import('./routes/ucitelj/profil/ucitelj-profil.component').then(c => c.UciteljProfilComponent),
      },
    ]
  },
  {
    path: appUrls.admin.template,
    canActivate: [authCheckGuard({tip: "ADMIN", error_redirect: appUrls.public({}).prijava({})})],
    loadComponent: () => import('./routes/admin/admin.component').then(c => c.AdminComponent),
    children: [
      {
        path: paths.index.template,
        loadComponent: () => import('./routes/admin/index/admin-index.component').then(c => c.AdminIndexComponent),
      },
    ]
  },
  {
    path: '**',
    loadComponent: public_import,
  },
];

for (const route of appRoutes) route.path = (route.path || "").replaceAll("/", "")

export default appRoutes
