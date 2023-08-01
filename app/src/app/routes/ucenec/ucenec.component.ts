import {Component, OnInit} from "@angular/core";
import {DefaultService} from "../../api";
import {AlertService} from "../../components/alert/alert.service";
import {NavGumb} from "../../components/nav-gumb/NavGumb";

@Component({
  selector: 'app-ucenec',
  templateUrl: './ucenec.component.html',
  styleUrls: ['./ucenec.component.scss']
})
export class UcenecComponent implements OnInit {
  private navStyle = "background-color: forestgreen; color: white"
  navGumbi: NavGumb[] = [
    {
      tekst: "Profil",
      ikona: "person",
      route: "/ucenec/profil",
      style: this.navStyle
    },
    {
      tekst: "Testi",
      ikona: "paper",
      route: "/ucenec",
      style: this.navStyle
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: "/ucenec/sporocila",
      style: this.navStyle
    },
    {
      tekst: "Zgodovina",
      ikona: "history",
      route: "/ucenec/zgodovina",
      style: this.navStyle
    }
  ];

  constructor(
    private alertService: AlertService,
    private defaultService: DefaultService
  ) {
  }

  ngOnInit() {
  }

}
