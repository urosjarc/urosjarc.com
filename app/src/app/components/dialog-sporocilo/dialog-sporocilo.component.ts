import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {DatePipe, NgStyle} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {SporociloInfo} from "./SporociloInfo";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-dialog-sporocilo',
  templateUrl: './dialog-sporocilo.component.html',
  styleUrls: ['./dialog-sporocilo.component.scss'],
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, NgStyle, MatListModule, DatePipe, MatIconModule],
})
export class DialogSporociloComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public sporociloInfo: SporociloInfo) {
  }

}
