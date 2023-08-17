import {Component, Input} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-geslo',
  templateUrl: './input-geslo.component.html',
  styleUrls: ['./input-geslo.component.scss']
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
