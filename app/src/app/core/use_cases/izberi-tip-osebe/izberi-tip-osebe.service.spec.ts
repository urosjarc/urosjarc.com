import { TestBed } from '@angular/core/testing';

import { IzberiTipOsebeService } from './izberi-tip-osebe.service';

describe('IzberiTipProfilaService', () => {
  let service: IzberiTipOsebeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IzberiTipOsebeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
