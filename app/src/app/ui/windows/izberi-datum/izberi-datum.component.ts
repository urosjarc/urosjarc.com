import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {DatePipe} from "@angular/common";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatCardModule} from "@angular/material/card";
import {DateOddaljenostPipe} from "../../pipes/date-oddaljenost/date-oddaljenost.pipe";

@Component({
  selector: 'app-dialog-izberi-datum',
  templateUrl: './dialog-izberi-datum.component.html',
  styleUrls: ['./dialog-izberi-datum.component.scss'],
  imports: [
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    DatePipe,
    DateOddaljenostPipe,
    MatDatepickerModule,
    MatCardModule
  ],
  standalone: true
})
export class IzberiDatumComponent {

  datum: Date = new Date()

  constructor(@Inject(MAT_DIALOG_DATA) datum: Date) {
    this.datum = datum
  }

}
