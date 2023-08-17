import {Component, Input} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-telefon',
  templateUrl: './input-telefon.component.html',
  styleUrls: ['./input-telefon.component.scss'],
  standalone: true
})
export class FormFieldTelefonComponent {
  @Input() formControl = new FormControl('', [
    Validators.required,
    Validators.minLength(9),
    Validators.pattern("[^a-zA-Z]+")
  ]);

  getErrorMessage() {
    if (this.formControl.hasError('required'))
      return 'Telefon je obvezen!';

    if (this.formControl.hasError("pattern"))
      return 'Telefon ni veljaven'

    return 'Telefonska je premajhna!'
  }
}
