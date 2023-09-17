import {String_vDate, String_vDuration} from "./String";

describe('utils: String test', () => {
  it('String_vDate () mora vrniti date, če datum vsebuje T, Z in uro', () => {
    const isoDate = '2023-09-17T08:30:00.000Z';
    const pricakovaniDatum = String_vDate(isoDate)
    expect(pricakovaniDatum instanceof Date).toBeTrue()

  });
  it(' String_vDate() mora vrniti datum z UTC oz. Z', () => {
    const isoDate = '2023-09-17T08:30:00.000';
    const pricakovaniDatum = new Date('2023-09-17T08:30:00.000Z');
    const datum = String_vDate(isoDate);
    expect(datum.toISOString()).toEqual(pricakovaniDatum.toISOString());
  });
  it('String_vDuration() mora vrniti čas trajanja v iso formatu', () => {
    // T= ZAČETEK TRAJANJA
    const isoTrajanje = 'PT2H30M';
    const parsedTrajanje = String_vDuration(isoTrajanje);

    expect(parsedTrajanje.hours).toBe(2);
    expect(parsedTrajanje.minutes).toBe(30);
  });

})
