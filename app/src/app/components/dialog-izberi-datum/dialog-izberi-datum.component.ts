import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {DatePipe} from "@angular/common";
import {DateOddaljenostPipe} from "../../pipes/DateOddaljenost/date-oddaljenost.pipe";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatCardModule} from "@angular/material/card";

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
export class DialogIzberiDatumComponent {

  deadline: Date = new Date()

  constructor(@Inject(MAT_DIALOG_DATA) deadline: Date) {
    this.deadline = deadline
  }

}
