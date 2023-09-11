import {DateOddaljenostClassPipe} from "./date-oddaljenost-class.pipe";
import * as moment from 'moment';
import {Moment} from "moment";

describe('Testiranje pipe: date-oddaljenost-class', () => {
  let pipe: DateOddaljenostClassPipe;
  // reprezentira funkcijo, ki nima parametrov in vrača moment
  let spyMoment: jasmine.Spy<() => moment.Moment>;
  let customDate: Moment;
  beforeEach(() => {

    pipe = new DateOddaljenostClassPipe();

  })

  it('mora ustvariti pipe', () => {
    expect(pipe).toBeTruthy();
  });
  it('mora vrniti pravi stil glede na število dni', () => {
    const poljubniDatum = moment().add(2, 'days').toDate();
    const poljubniDatum2 = moment().add(5, 'days').toDate();
    const poljubniDatum3 = moment().add(10, 'days').toDate();

    expect(pipe.transform(poljubniDatum)).toEqual('danger-pulse');
    expect(pipe.transform(poljubniDatum2)).toEqual('warn-pulse');
    expect(pipe.transform(poljubniDatum3)).toEqual('');
  });

})
