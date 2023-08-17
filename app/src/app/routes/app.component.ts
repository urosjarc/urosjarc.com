import {Component, OnInit} from '@angular/core';
import {AlertService} from "../services/alert/alert.service";
import {MatDialog} from "@angular/material/dialog";
import {DialogAlertComponent} from "../components/dialog-alert/dialog-alert.component";
import {Alert} from "../services/alert/Alert";
import {trace} from "../utils";
import {NavigationStart, Router} from "@angular/router";

@Component({
  selector: 'app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(
    private router: Router,
    private dialog: MatDialog,
    private alertService: AlertService) {

    this.router.events.subscribe({
      next(value) {
        if (value instanceof NavigationStart) {
          console.groupEnd()
          console.group(`${value.id} ${value.url}`)
        }
      }
    })
  }

  @trace()
  ngOnInit() {
    this.alertService.alerts.subscribe((alert: Alert) => {
      if (alert) this.onAlert(alert)
    })
  }

  @trace()
  private onAlert(data: Alert) {
    this.dialog.open<any, Alert>(DialogAlertComponent, {
      enterAnimationDuration: 250,
      exitAnimationDuration: 500,
      data: data
    });
  }
}
