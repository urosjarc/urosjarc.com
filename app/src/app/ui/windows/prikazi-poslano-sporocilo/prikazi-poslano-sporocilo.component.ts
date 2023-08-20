import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {DatePipe, NgStyle} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {SporociloModel} from "../../../../assets/models/SporociloModel";

@Component({
  selector: 'app-prikazi-poslano-sporocilo',
  templateUrl: './prikazi-poslano-sporocilo.component.html',
  styleUrls: ['./prikazi-poslano-sporocilo.component.scss'],
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, NgStyle, MatListModule, DatePipe, MatIconModule],
})
export class PrikaziPoslanoSporociloComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public sporociloInfo: SporociloModel) {
  }

}
