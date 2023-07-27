import {Component} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-telefon',
  templateUrl: './input-telefon.component.html',
  styleUrls: ['./input-telefon.component.scss']
})
export class InputTelefonComponent {
  telefon = new FormControl('', [Validators.required, Validators.minLength(9)]);

  getErrorMessage() {
    if (this.telefon.hasError('required')) {
      return 'You must enter a value';
    } else if (this.telefon.hasError('minLength')) {
      return 'Not a valid email';
    }
    return "Error"
  }
}
