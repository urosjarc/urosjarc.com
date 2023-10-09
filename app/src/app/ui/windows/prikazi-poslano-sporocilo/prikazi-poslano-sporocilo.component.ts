import {Component} from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {DatePipe, NgStyle} from "@angular/common";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {SporociloModel} from "../../../core/domain/SporociloModel";
import {WindowModel} from "../../../utils/WindowModel";

@Component({
  selector: 'app-prikazi-poslano-sporocilo',
  templateUrl: './prikazi-poslano-sporocilo.component.html',
  styleUrls: ['./prikazi-poslano-sporocilo.component.scss'],
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, NgStyle, MatListModule, DatePipe, MatIconModule],
})
export class PrikaziPoslanoSporociloComponent extends WindowModel<SporociloModel> {
}
