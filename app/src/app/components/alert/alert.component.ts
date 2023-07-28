import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {Alert} from "./alert";

@Component({
  selector: 'app-alert',
  templateUrl: 'alert.component.html',
  styleUrls: ['alert.component.scss'],
  standalone: true,
  imports: [MatDialogModule, MatButtonModule],
})
export class AlertComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Alert) {}
}
