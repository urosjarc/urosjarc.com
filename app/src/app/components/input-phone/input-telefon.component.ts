import {Component, Input} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-telefon',
  templateUrl: './input-telefon.component.html',
  styleUrls: ['./input-telefon.component.scss']
})
export class InputTelefonComponent {
  @Input() telefon = new FormControl('', [Validators.required, Validators.minLength(9)]);

  getErrorMessage() {
    if (this.telefon.hasError('required')) {
      return 'Telefon je obvezen!';
    }
    return 'Telefon ni veljaven!'
  }
}
