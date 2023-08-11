import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export function Validator_besede(stevilo_besed: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const words = control.value.trim().split(/\s+/)
    return words.length < stevilo_besed ? {"Validator_besed": "Premalo število besed!"} : null;
  };
}
