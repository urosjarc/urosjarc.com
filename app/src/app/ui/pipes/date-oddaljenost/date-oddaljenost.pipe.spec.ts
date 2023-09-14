import {DateOddaljenostPipe} from "./date-oddaljenost.pipe";
import * as moment from 'moment';
// TODO: ne zam uporabiti moment.locale($localize.locale) za slovenski prevod
describe('pipes date-oddaljenost testi', () => {
  let pipe: DateOddaljenostPipe;
  beforeEach(() => {
    pipe = new DateOddaljenostPipe();
    moment.locale('sl');
    moment.locale($localize.locale)

  })

  it('mora ustvariti pipe', () => {
    const currentLocale = moment.locale($localize.locale);
    expect(pipe).toBeTruthy();
  });

  it('mora vrniti string', () => {
    const datum = '2023-05-01';

    expect(typeof pipe.transform(new Date(datum))).toEqual('string')
  });

  it('mora vrniti pravo obliko sporočila ', () => {
    const datumVPrihodnjost = moment().add(10, 'days').toDate();
    expect(pipe.transform(new Date(datumVPrihodnjost))).toEqual('čez 10 dni')
  });
  it('mora vrniti pravo obliko sporočila ', () => {
    const datumVPrihodnjost = moment().add(-10, 'days').toDate();
    expect(pipe.transform(new Date(datumVPrihodnjost))).toEqual('pred 10 dnevi')
  });
})
