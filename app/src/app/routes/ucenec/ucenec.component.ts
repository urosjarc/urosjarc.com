import {Component} from "@angular/core";
import {
  IzbrisiUporabniskePodatkeService
} from "../../core/use_cases/izbrisi-uporabniske-podatke/izbrisi-uporabniske-podatke.service";
import {ToolbarNavigacijaComponent} from "../../ui/parts/toolbars/toolbar-navigacija/toolbar-navigacija.component";
import {RouterOutlet} from "@angular/router";
import {ButtonToolbarModel} from "../../ui/parts/buttons/button-toolbar/button-toolbar.model";
import {routes} from "../../routes";
import {trace} from "../../utils/trace";

@Component({
  selector: 'app-ucenec',
  templateUrl: './ucenec.component.html',
  styleUrls: ['./ucenec.component.scss'],
  imports: [
    ToolbarNavigacijaComponent,
    RouterOutlet
  ],
  standalone: true
})
export class UcenecComponent {
  private navStyle = "background-color: forestgreen; color: white"
  buttonToolbarModels: ButtonToolbarModel[] = [
    {
      tekst: "Nazaj",
      ikona: "reply",
      route: routes.public({}).$,
    },
    {
      tekst: "Testi",
      ikona: "edit",
      route: routes.ucenec({}).testi({}).$,
      style: this.navStyle
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: routes.ucenec({}).sporocila({}).$,
      style: this.navStyle
    },
    {
      tekst: "Delo",
      ikona: "work",
      route: routes.ucenec({}).delo({}).$,
      style: this.navStyle
    },
    {
      tekst: "Profil",
      ikona: "person",
      route: routes.ucenec({}).profil({}).$,
      style: this.navStyle
    },
    {
      tekst: "Odjava",
      ikona: "close",
      onClick: () => {
        this.odjava()
      }
    },
  ];

  constructor(private izbrisiUporabniskePodatkeService: IzbrisiUporabniskePodatkeService) {
  }

  @trace()
  odjava() {
    this.izbrisiUporabniskePodatkeService.zdaj()
  }
}
