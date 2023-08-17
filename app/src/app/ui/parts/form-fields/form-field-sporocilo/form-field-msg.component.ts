import {Component, Input} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {Validator_besede} from "../../validators/besede.validator";

@Component({
  selector: 'app-input-msg',
  templateUrl: './input-msg.component.html',
  styleUrls: ['./input-msg.component.scss']
})
export class FormFieldMsgComponent {
  @Input() formControl = new FormControl('', [
    Validators.required,
    Validators.minLength(10),
    Validator_besede(3)
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
