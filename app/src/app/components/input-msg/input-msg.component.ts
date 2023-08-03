import {Component, Input} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-msg',
  templateUrl: './input-msg.component.html',
  styleUrls: ['./input-msg.component.scss']
})
export class InputMsgComponent {
  @Input() formControl = new FormControl('', [
    Validators.required,
    Validators.minLength(10),
    Validators.pattern(".*\\S+\\s+\\S+\\s+\\S+.*")
  ]);

  getErrorMessage() {
    if (this.formControl.hasError('required')) {
      return 'Sporočilo je obvezno!';
    }
    if (this.formControl.hasError('minlength')){
      return 'Sporočilo je premajhno!';
    }
    if (this.formControl.hasError('pattern')){
      return 'Sporočilo ima premalo besed!';
    }

    return 'Sporočilo ni veljavno!'
  }
}
