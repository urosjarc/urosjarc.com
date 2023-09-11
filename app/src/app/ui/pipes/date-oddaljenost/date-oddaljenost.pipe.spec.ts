import {DateOddaljenostPipe} from "./date-oddaljenost.pipe";
import * as moment from 'moment';

describe('pipes date-oddaljenost testi', () => {
  let pipe: DateOddaljenostPipe;
  beforeEach(() => {
    pipe = new DateOddaljenostPipe();
  })

  it('mora ustvariti pipe', () => {
    expect(pipe).toBeTruthy();
  });

  it('mora vrniti moment', () => {
    const datum = '2023-05-01';

    expect(typeof pipe.transform(new Date(datum))).toEqual('string')
  });
  it('mora vrniti pravo obliko sporočila ', () => {
    const datumVPrihodnjost = moment().add(10, 'days').toDate();

    expect(pipe.transform(new Date(datumVPrihodnjost))).toEqual('in 10 days')
  });
})
