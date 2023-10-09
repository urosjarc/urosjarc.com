import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-form-field-telefon',
  templateUrl: './form-field-telefon.component.html',
  styleUrls: ['./form-field-telefon.component.scss'],
  imports: [
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    NgIf
  ],
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
      return 'Telefon ni veljaven!'

    return 'Telefonska je premajhna!'
  }
}
