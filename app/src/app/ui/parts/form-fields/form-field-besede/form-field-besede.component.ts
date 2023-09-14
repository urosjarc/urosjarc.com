import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-form-field-besede',
  templateUrl: './form-field-besede.component.html',
  styleUrls: ['./form-field-besede.component.scss'],
  imports: [
    MatInputModule,
    ReactiveFormsModule,
    NgIf,
    MatIconModule
  ],
  standalone: true
})
export class FormFieldBesedeComponent {
  @Input() formControl = new FormControl('', [
    Validators.required,
    Validators.minLength(4)
  ]);
  @Input() label: string = "Vsebina";
  @Input() icon: string = "abc";

  getErrorMessage() {
    if (this.formControl.hasError('required')) {
      return 'Vsebina je obvezna!';
    }
    if (this.formControl.hasError('minlength')) {
      return 'Vsebina je premajhna!';
    }

    return 'Sporočilo ni veljavno!'
  }
}
