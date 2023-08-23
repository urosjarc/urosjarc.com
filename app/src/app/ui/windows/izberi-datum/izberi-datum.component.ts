import {Component} from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";
import {MatCardModule} from "@angular/material/card";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatButtonModule} from "@angular/material/button";
import {MatNativeDateModule} from "@angular/material/core";
import {WindowModel} from "../../../utils/WindowModel";
import {IzberiDatumModel} from "./izberi-datum.model";

@Component({
  selector: 'app-izberi-datum',
  templateUrl: './izberi-datum.component.html',
  styleUrls: ['./izberi-datum.component.scss'],
  standalone: true,
  imports: [
    MatDialogModule,
    MatCardModule,
    MatButtonModule,
    MatNativeDateModule,
    MatDatepickerModule,
  ]
})
export class IzberiDatumComponent extends WindowModel<IzberiDatumModel> {
}
