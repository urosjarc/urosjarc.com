import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {KoledarComponent} from "./routes/public/koledar/koledar.component";
import {KontaktComponent} from "./routes/public/kontakt/kontakt.component";
import {PrijavaComponent} from "./routes/public/prijava/prijava.component";
import {PublicComponent} from "./routes/public/index/public.component";

const routes: Routes = [
  {path: '', component: PublicComponent},
  {path: 'koledar', component: KoledarComponent},
  {path: 'kontakt', component: KontaktComponent},
  {path: 'prijava', component: PrijavaComponent},
  {path: '**', component: PublicComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
