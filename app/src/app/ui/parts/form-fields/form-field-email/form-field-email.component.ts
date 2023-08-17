import {Component, Input} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-email',
  templateUrl: './input-email.component.html',
  styleUrls: ['./input-email.component.scss']
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
