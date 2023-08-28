import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";
import {MatDatepickerModule} from "@angular/material/datepicker";

@Component({
  selector: 'app-form-field-datum',
  templateUrl: './form-field-datum.component.html',
  styleUrls: ['./form-field-datum.component.scss'],
  imports: [
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    NgIf,
    MatDatepickerModule
  ],
  standalone: true
})
export class FormFieldDatumComponent {
  formControl: FormControl<Date | null> = new FormControl(null, [Validators.required]);
  @Input() label: string = "Datum";

  getErrorMessage() {
    if (this.formControl.hasError('required'))
      return 'Datum je obvezen!';

    return 'Datum ni veljaven!'
  }
}
