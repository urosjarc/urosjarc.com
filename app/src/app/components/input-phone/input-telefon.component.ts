import {Component, Input} from '@angular/core';
import {FormControl, ValidatorFn, Validators} from "@angular/forms";

@Component({
  selector: 'app-input-telefon',
  templateUrl: './input-telefon.component.html',
  styleUrls: ['./input-telefon.component.scss']
})
export class InputTelefonComponent {
  @Input() telefon = new FormControl('', [
    Validators.required,
    Validators.minLength(9),
    Validators.pattern("[^a-zA-Z]+")
  ]);

  getErrorMessage() {
    if (this.telefon.hasError('required')) {
      return 'Telefon je obvezen!';
    }
    console.log(this.telefon.errors)
    if(this.telefon.hasError('minLength')){
      return 'Telefon je premajhen!'
    }
    return 'Telefon ni veljaven!'
  }
}
