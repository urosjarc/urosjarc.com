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
  navGumbi: NavGumb[] = [
    {
      tekst: "Profil",
      ikona: "person",
      route: "/ucenec/profil"
    },
    {
      tekst: "Testi",
      ikona: "paper",
      route: "/ucenec/testi"
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: "/ucenec/sporocila"
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
