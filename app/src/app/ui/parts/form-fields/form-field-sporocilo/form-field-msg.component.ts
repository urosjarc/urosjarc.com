import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {Validator_stevilo_besed} from "../../../validators/stevilo-besed/stevilo-besede.validator";
import {MatInputModule} from "@angular/material/input";

@Component({
  selector: 'app-form-field-msg',
  templateUrl: './form-field-msg.component.html',
  styleUrls: ['./form-field-msg.component.scss'],
  imports: [
    MatInputModule,
    ReactiveFormsModule
  ],
  standalone: true
})
export class FormFieldMsgComponent {
  @Input() formControl = new FormControl('', [
    Validators.required,
    Validators.minLength(10),
    Validator_stevilo_besed(3)
  ]);

  getErrorMessage() {
    if (this.formControl.hasError('required')) {
      return 'Sporočilo je obvezno!';
    }
    if (this.formControl.hasError('minlength')){
      return 'Sporočilo je premajhno!';
    }
    if (this.formControl.hasError('Validator_besed')){
      return 'Sporočilo ima premalo besed!';
    }

    return 'Sporočilo ni veljavno!'
  }
}
