import { TestBed } from '@angular/core/testing';

import { SinhronizirajUporabniskePodatkeService } from './sinhroniziraj-uporabniske-podatke.service';

describe('SinhronizirajUporabniskePodatkeService', () => {
  let service: SinhronizirajUporabniskePodatkeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SinhronizirajUporabniskePodatkeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
