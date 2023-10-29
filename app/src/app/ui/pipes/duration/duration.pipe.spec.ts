import * as moment from "moment/moment";
import {Moment} from "moment/moment";
import {DurationPipe} from "./duration.pipe";

describe('Testiranje pipe: date-oddaljenost-class', () => {
  let pipe: DurationPipe;
  // reprezentira funkcijo, ki nima parametrov in vrača moment
  let spyMoment: jasmine.Spy<() => moment.Moment>;
  let customDate: Moment;
  beforeEach(() => {

    pipe = new DurationPipe();

  })
  it('mora ustvariti pipe', () => {
    expect(pipe).toBeTruthy();
  });

  it('mora vrniti string', () => {
    const trajanje= moment.duration(2, 'hours')
    expect(typeof pipe.transform(trajanje)).toEqual('string');
  });
})
