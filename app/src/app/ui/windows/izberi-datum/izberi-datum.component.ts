import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule} from "@angular/material/dialog";
import {MatCardModule} from "@angular/material/card";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatButtonModule} from "@angular/material/button";
import {MatNativeDateModule, NativeDateAdapter} from "@angular/material/core";

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
export class IzberiDatumComponent {

  datum: Date = new Date()

  constructor(@Inject(MAT_DIALOG_DATA) datum: Date) {
    this.datum = datum
  }

}
