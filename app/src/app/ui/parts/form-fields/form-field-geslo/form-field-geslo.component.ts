import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-form-field-geslo',
  templateUrl: './form-field-geslo.component.html',
  styleUrls: ['./form-field-geslo.component.scss'],
  imports: [
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    NgIf
  ],
  standalone: true
})
export class FormFieldGesloComponent {
  @Input() formControl = new FormControl('', [Validators.required, Validators.minLength(4)]);

  hide = true;
  get value() { return this.formControl.getRawValue() }

  getErrorMessage() {
    if (this.formControl.hasError('required')) {
      return 'Geslo je obvezno!';
    }

    return 'Geslo je premajhno!';
  }
}
