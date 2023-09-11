import {StatusTipStylePipe} from "./statusTip-style.pipe";
import {Status} from "../../../core/services/api/models/status";

describe('Testiranje pipe: status-Tip-style', () => {
  let pipe: StatusTipStylePipe;
  const statusniTipiVrste: ("NAPACNO" | "PRAVILNO" | "NEZACETO" | "NERESENO")[] = ['NERESENO', 'NAPACNO', 'PRAVILNO'];

  beforeEach(() => {

    pipe = new StatusTipStylePipe();

  })
  it('mora ustvariti pipe', () => {
    expect(pipe).toBeTruthy();
  });

  for (const tip of statusniTipiVrste) {
    it(`mora vrniti string ob inputu ${tip}`, () => {
      expect(typeof pipe.transform(tip)).toEqual('string');

    });
  }
})
