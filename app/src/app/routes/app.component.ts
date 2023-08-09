import {Component, OnInit} from '@angular/core';
import {AlertService} from "../services/alert/alert.service";
import {MatDialog} from "@angular/material/dialog";
import {AlertComponent} from "../components/alert/alert.component";
import {Alert} from "../services/alert/Alert";
import {trace} from "../utils";

@Component({
  selector: 'app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(
    private dialog: MatDialog,
    private alertService: AlertService) {
  }

  @trace()
  ngOnInit() {
    this.alertService.alerts.subscribe((alert: Alert) => {
      if (alert) this.onAlert(alert)
    })
  }

  @trace()
  private onAlert(data: Alert) {
    this.dialog.open(AlertComponent, {
      enterAnimationDuration: 250,
      exitAnimationDuration: 500,
      data: data
    });
  }

}
