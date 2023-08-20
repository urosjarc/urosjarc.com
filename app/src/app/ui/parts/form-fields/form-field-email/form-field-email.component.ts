import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-form-field-email',
  templateUrl: './form-field-email.component.html',
  styleUrls: ['./form-field-email.component.scss'],
  imports: [
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule
  ],
  standalone: true
})
export class FormFieldEmailComponent {
  @Input() formControl = new FormControl('', [Validators.required, Validators.email]);

  getErrorMessage() {
    if (this.formControl.hasError('required')) {
      return 'Email je obvezen!';
    }

    return 'Email ni veljaven!';
  }
}
