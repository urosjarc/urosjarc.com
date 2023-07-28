import {Component, Input, OnInit} from '@angular/core';
import {NavGumb} from "../components/nav-gumb/nav-gumb";
import {Alert} from "../components/alert/alert";
import {AlertService} from "../components/alert/alert.service";
import {MatDialog} from "@angular/material/dialog";
import {AlertComponent} from "../components/alert/alert.component";

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['./root.component.scss']
})
export class RootComponent implements OnInit {
  @Input() navGumbi: NavGumb[] = [
    {
      tekst: "Domov",
      ikona: "home",
      route: "/"
    },
    {
      tekst: "Koledar",
      ikona: "calendar_month",
      route: "/koledar"
    },
    {
      tekst: "Kontakt",
      ikona: "mail",
      route: "/kontakt"
    },
    {
      tekst: "Prijava",
      ikona: "lock",
      route: "/prijava"
    },
  ]

  constructor(
    private dialog: MatDialog,
    private alertService: AlertService) {
  }

  ngOnInit() {
    this.alertService.alerts.subscribe(alert => {
      if (alert) this.onAlert(alert)
    })
  }

  private onAlert(data: Alert) {
    this.dialog.open(AlertComponent, {
      enterAnimationDuration: 250,
      exitAnimationDuration: 500,
      data: data
    });
  }

}
