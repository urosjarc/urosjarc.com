import {Validator_stevilo_besed} from "./stevilo-besede.validator";
import {FormControl, ValidationErrors} from "@angular/forms";

describe('Validator: stevilo-besed test', () => {
  const validator = Validator_stevilo_besed(3);
  const control = (besediloMsg:  string) => {return new FormControl(besediloMsg);}
  it('mora vrniti error msg, če je prisotnih premalo besed', () => {
    const result: ValidationErrors | null = validator(control('Premalo besed'));
    const errorSporocilo = result && result['Validator_besed'];
    expect(typeof errorSporocilo).toEqual('string');
  });

  it('mora vrniti null, če sporočilo vsebuje zadostno število besed', () => {
    const result: ValidationErrors | null = validator(control('Pravilni format sporocila'));
    const errorSporocilo = result && result['Validator_besed'];
    expect(errorSporocilo).toBeNull();
  });
  it('edge case test1', () => {
    const result: ValidationErrors | null = validator(control(' '));
    const errorSporocilo = result && result['Validator_besed'];
    expect(typeof errorSporocilo).toEqual('string');
  })
  it('edge case test2', () => {
    const result: ValidationErrors | null = validator(control('%&#""#%'));
    const errorSporocilo = result && result['Validator_besed'];
    expect(typeof errorSporocilo).toEqual('string');
  })

})
