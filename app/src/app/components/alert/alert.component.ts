import {Component, OnInit} from '@angular/core';
import {AlertService} from "./alert.service";
import {takeWhile} from "rxjs";
import {Alert} from "./alert";

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss'],
})
export class AlertComponent implements OnInit {
  private _subscribed: boolean = true;

  constructor(private alertService: AlertService) {
  }

  ngOnInit() {
    this.alertService.alerts
      .pipe(takeWhile(() => this._subscribed))
      .subscribe(notification => {
        if (notification) this.render(notification);
      });
  }

  ngOnDestroy() {
    this._subscribed = false;
  }

  private render(notification: Alert) {
    alert(notification)
  }
}
