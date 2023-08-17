import { StatusClassPipe } from './status-color.pipe';

describe('StatusColorPipe', () => {
  it('create an instance', () => {
    const pipe = new StatusClassPipe();
    expect(pipe).toBeTruthy();
  });
});
