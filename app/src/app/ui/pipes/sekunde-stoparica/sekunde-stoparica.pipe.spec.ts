import {SekundeStoparicaPipe} from "./sekunde-stoparica.pipe";

describe('Testiranje pipe: date-oddaljenost-class', () => {
  let pipe: SekundeStoparicaPipe;

  beforeEach(() => {

    pipe = new SekundeStoparicaPipe();

  })
  it('mora ustvariti pipe', () => {
    expect(pipe).toBeTruthy();
  });
  it('mora vrniti pravi format in vrednost', () => {
    const sekunde = 200;
    expect(pipe.transform(sekunde)).toEqual('03:20')
  });
})
