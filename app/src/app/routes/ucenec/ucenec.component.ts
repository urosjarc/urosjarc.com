import {Component} from "@angular/core";
import {
  IzbrisiUporabniskePodatkeService
} from "../../core/use_cases/izbrisi-uporabniske-podatke/izbrisi-uporabniske-podatke.service";
import {ToolbarNavigacijaComponent} from "../../ui/parts/toolbars/toolbar-navigacija/toolbar-navigacija.component";
import {RouterOutlet} from "@angular/router";
import {ButtonToolbarModel} from "../../ui/parts/buttons/button-toolbar/button-toolbar.model";
import {appUrls} from "../../app.urls";
import {trace} from "../../utils/trace";
import {CardNavigacijaComponent} from "../../ui/parts/cards/card-navigacija/card-navigacija.component";

@Component({
  selector: 'app-ucenec',
  templateUrl: './ucenec.component.html',
  styleUrls: ['./ucenec.component.scss'],
  imports: [
    ToolbarNavigacijaComponent,
    RouterOutlet,
    CardNavigacijaComponent
  ],
  standalone: true
})
export class UcenecComponent {
  private navStyle = "background-color: forestgreen; color: white"
  buttonToolbarModels: ButtonToolbarModel[] = [
    {
      tekst: "Nazaj",
      ikona: "reply",
      route: appUrls.public({}).$,
    },
    {
      tekst: "Testi",
      ikona: "edit",
      route: appUrls.ucenec({}).testi({}).$,
      style: this.navStyle
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: appUrls.ucenec({}).sporocila({}).$,
      style: this.navStyle
    },
    {
      tekst: "Delo",
      ikona: "work",
      route: appUrls.ucenec({}).delo({}).$,
      style: this.navStyle
    },
    {
      tekst: "Profil",
      ikona: "person",
      route: appUrls.ucenec({}).profil({}).$,
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
