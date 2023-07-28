import {Component, Input} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-geslo',
  templateUrl: './input-geslo.component.html',
  styleUrls: ['./input-geslo.component.scss']
})
export class InputGesloComponent {
  @Input() geslo = new FormControl('', [Validators.required, Validators.minLength(4)]);

  getErrorMessage() {
    if (this.geslo.hasError('required')) {
      return 'You must enter a value';
    }

    return this.geslo.hasError('geslo') ? 'Not a valid email' : '';
  }
}
