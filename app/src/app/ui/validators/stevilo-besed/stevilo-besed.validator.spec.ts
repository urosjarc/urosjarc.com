import {Validator_stevilo_besed} from "./stevilo-besede.validator";
import {FormControl, ValidationErrors} from "@angular/forms";

describe('Validator: stevilo-besed test', () => {
  it('mora vrniti error msg, če je prisotnih premalo besed', () => {
    const validator = Validator_stevilo_besed(3);
    const control = new FormControl('Premalo besed');

    const result: ValidationErrors | null = validator(control);
    const errorSporocilo = result && result['Validator_besed'];
    expect(typeof errorSporocilo).toEqual('string')
  });

  it('mora vrniti error msg, če je prisotnih premalo besed', () => {
    const validator = Validator_stevilo_besed(3);
    const control = new FormControl('Pravilni format sporocila');

    const result: ValidationErrors | null = validator(control);
    const errorSporocilo = result && result['Validator_besed'];
    expect(errorSporocilo).toBeNull()
  });

})
