import {Component, Input} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-msg',
  templateUrl: './input-msg.component.html',
  styleUrls: ['./input-msg.component.scss']
})
export class InputMsgComponent {
  @Input() formControl = new FormControl('', [Validators.required, Validators.minLength(10)]);

  getErrorMessage() {
    if (this.formControl.hasError('required')) {
      return 'Sporočilo je obvezno!';
    }
    return 'Sporočilo je premajhno!';
  }
}
