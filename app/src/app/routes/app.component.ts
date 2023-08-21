import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {NavigationStart, Router, RouterOutlet} from "@angular/router";
import {AlertService} from "../core/services/alert/alert.service";
import {trace} from "../utils/trace";
import {AlertServiceModel} from "../core/services/alert/alert.service.model";
import {PrikaziAlertComponent} from "../ui/windows/prikazi-alert/prikazi-alert.component";

@Component({
  selector: 'app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [
    RouterOutlet,
  ],
  standalone: true
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
    this.alertService.alerts.subscribe((alert: AlertServiceModel) => {
      if (alert) this.onAlert(alert)
    })
  }

  @trace()
  private onAlert(data: AlertServiceModel) {
    this.dialog.open<any, AlertServiceModel>(PrikaziAlertComponent, {
      enterAnimationDuration: 250,
      exitAnimationDuration: 500,
      data: data
    });
  }
}
