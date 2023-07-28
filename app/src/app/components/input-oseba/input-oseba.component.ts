import {Component, Input} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-oseba',
  templateUrl: './input-oseba.component.html',
  styleUrls: ['./input-oseba.component.scss']
})
export class InputOsebaComponent {
  @Input() label: string = "Vnesi ime in priimek..."
  @Input() oseba = new FormControl('', [Validators.required, Validators.pattern("[a-zA-Z]+\\s[a-zA-Z]+")]);
  getErrorMessage() {
    if (this.oseba.hasError('required')) {
      return 'You must enter a value';
    }

    return this.oseba.hasError('pattern') ? 'Not a valid ime in priimek' : 'Napaka';
  }

}
