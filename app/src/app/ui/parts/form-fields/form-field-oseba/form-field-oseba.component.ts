import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-form-field-oseba',
  templateUrl: './form-field-oseba.component.html',
  styleUrls: ['./form-field-oseba.component.scss'],
  imports: [
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule
  ],
  standalone: true
})
export class FormFieldOsebaComponent {
  @Input() label: string = "Ime in priimek"
  @Input() formControl = new FormControl('', [Validators.required, Validators.pattern("[a-zA-Z]+\\s+[a-zA-Z]+")]);
  getErrorMessage() {
    if (this.formControl.hasError('required')) {
      return 'Vnos je obvezen!';
    }
    return 'Vnos nima dveh veljavnih besed!';
  }
}
