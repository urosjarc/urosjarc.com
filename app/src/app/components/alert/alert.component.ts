import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {NgStyle} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {Alert} from "../../services/alert/Alert";

@Component({
  selector: 'app-alert',
  templateUrl: 'alert.component.html',
  styleUrls: ['alert.component.scss'],
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, NgStyle, MatListModule],
})
export class AlertComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Alert) {
  }
}
