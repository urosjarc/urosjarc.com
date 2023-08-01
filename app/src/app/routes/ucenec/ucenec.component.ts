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
      tekst: "Testi",
      ikona: "edit",
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
      tekst: "Delo",
      ikona: "work",
      route: "/ucenec/delo",
      style: this.navStyle
    },
    {
      tekst: "Profil",
      ikona: "person",
      route: "/ucenec/profil",
      style: this.navStyle
    },
    {
      tekst: "Odjava",
      ikona: "close",
      route: "/prijava"
    },
  ];

  constructor(
    private alertService: AlertService,
    private defaultService: DefaultService
  ) {
  }

  ngOnInit() {
  }

}
